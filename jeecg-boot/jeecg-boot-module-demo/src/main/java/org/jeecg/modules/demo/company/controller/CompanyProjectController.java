package org.jeecg.modules.demo.company.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.*;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.company.entity.CompanyProject;
import org.jeecg.modules.demo.company.entity.CompanyProjectEventRecord;
import org.jeecg.modules.demo.company.entity.CompanyProjectImage;
import org.jeecg.modules.demo.company.entity.CompanyProjectSchedule;
import org.jeecg.modules.demo.company.service.ICompanyProjectEventRecordService;
import org.jeecg.modules.demo.company.service.ICompanyProjectImageService;
import org.jeecg.modules.demo.company.service.ICompanyProjectScheduleService;
import org.jeecg.modules.demo.company.service.ICompanyProjectService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 公司项目列表
 * @Author: jeecg-boot
 * @Date: 2021-06-17
 * @Version: V1.0
 */
@Api(tags = "公司项目列表")
@RestController
@RequestMapping("/company/companyProject")
@Slf4j
public class CompanyProjectController extends JeecgController<CompanyProject, ICompanyProjectService> {
    @Autowired
    private ICompanyProjectService companyProjectService;
    @Autowired
    private ICompanyProjectImageService companyProjectImageService;
    @Autowired
    private ICompanyProjectEventRecordService companyProjectEventRecordService;
    @Autowired
    private ICompanyProjectScheduleService companyProjectScheduleService;
    private static final String SUPER_QUERY_PARAMS = "superQueryParams";
    private static final String SUPER_QUERY_MATCH_TYPE = "superQueryMatchType";

    /**
     * 全部列表查询
     */
    @AutoLog(value = "公司项目列表-全部列表查询")
    @ApiOperation(value = "公司项目列表-全部列表查询", notes = "公司项目列表-全部列表查询")
    @GetMapping(value = "/queryList")
    public Result<?> queryList() {
        List<CompanyProject> list = companyProjectService.list();
        return Result.OK(list);
    }

    /**
     * 分页列表查询
     *
     * @param companyProject
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "公司项目列表-分页列表查询")
    @ApiOperation(value = "公司项目列表-分页列表查询", notes = "公司项目列表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(CompanyProject companyProject,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        Page<CompanyProject> page = new Page<CompanyProject>(pageNo, pageSize);
        // 单表查询
        // QueryWrapper<CompanyProject> queryWrapper = QueryGenerator.initQueryWrapper(companyProject, req.getParameterMap());
        // IPage<CompanyProject> pageList = companyProjectService.page(page, queryWrapper);

        // 多表查询
//        Map<String, String[]> parameterMap = req.getParameterMap();
//        if (parameterMap != null && parameterMap.containsKey(SUPER_QUERY_PARAMS)) {
//            String superQueryParams = parameterMap.get(SUPER_QUERY_PARAMS)[0];
//            String superQueryMatchType = parameterMap.get(SUPER_QUERY_MATCH_TYPE) != null ? parameterMap.get(SUPER_QUERY_MATCH_TYPE)[0] : MatchTypeEnum.AND.getValue();
//            MatchTypeEnum matchType = MatchTypeEnum.getByValue(superQueryMatchType);
//            // update-begin--Author:sunjianlei  Date:20200325 for：高级查询的条件要用括号括起来，防止和用户的其他条件冲突 -------
//            try {
//                superQueryParams = URLDecoder.decode(superQueryParams, "UTF-8");
//                List<QueryCondition> conditions = JSON.parseArray(superQueryParams, QueryCondition.class);
//                for (int i = 0; i < conditions.size(); i++) {
//                    QueryCondition rule = conditions.get(i);
//                    if (oConvertUtils.isNotEmpty(rule.getField())
//                            && oConvertUtils.isNotEmpty(rule.getRule())
//                            && oConvertUtils.isNotEmpty(rule.getVal())) {
//                        System.err.println(rule.getField());
//                        Object queryValue = rule.getVal();
//                        if ("date".equals(rule.getType())) {
//                            queryValue = DateUtils.str2Date(rule.getVal(), DateUtils.date_sdf.get());
//                        } else if ("datetime".equals(rule.getType())) {
//                            queryValue = DateUtils.str2Date(rule.getVal(), DateUtils.datetimeFormat.get());
//                        }
//                        Class cls = companyProject.getClass();
//                        Field[] fields = cls.getDeclaredFields();
//                        for (int j = 0; j < fields.length; j++) {
//                            Field f = fields[j];
//                            f.setAccessible(true);
//                            System.out.println("属性名:" + f.getName());
//                            if(f.getName().equals(rule.getField())) {
//                                companyProject.setProjectName(rule.getField());
//                            }
//                        }
//                        System.err.println(queryValue);
//                    }
//                }
//            } catch (UnsupportedEncodingException e) {
//                log.error("--高级查询参数转码失败：" + superQueryParams, e);
//            } catch (Exception e) {
//                log.error("--高级查询拼接失败：" + e.getMessage());
//                e.printStackTrace();
//            }
//        }
        Map<String, Object> map = com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(companyProject), Map.class);
        IPage<CompanyProject> pageList = companyProjectService.pageByUnitAndType(page, map);
        pageList.getRecords().stream().forEach(i -> {
            // 根据id项目查询图片列表
            List<CompanyProjectImage> companyProjectImages = companyProjectImageService.getProjectById(i.getId());
            i.setCompanyProjectImageList(companyProjectImages);
            // 根据id项目查询事件记录列表
            List<CompanyProjectEventRecord> companyProjectEventRecords = companyProjectEventRecordService.getProjectById(i.getId());
            i.setCompanyProjectEventRecords(companyProjectEventRecords);

            // 根据id项目查询项目进度列表
            List<CompanyProjectSchedule> companyProjectSchedules = companyProjectScheduleService.getProjectById(i.getId());
            i.setCompanyProjectSchedules(companyProjectSchedules);
            if (companyProjectSchedules.size() > 0) {
                BigDecimal schedule = new BigDecimal(0);

                BigDecimal sumWeight = new BigDecimal(0);
                BigDecimal totalWeight = new BigDecimal(0);
                for (CompanyProjectSchedule companyProjectSchedule : companyProjectSchedules) {
                    sumWeight = sumWeight.add(new BigDecimal(companyProjectSchedule.getWeight()).multiply(new BigDecimal(companyProjectSchedule.getSchedule())));
                    totalWeight = totalWeight.add(new BigDecimal(companyProjectSchedule.getWeight()));
                }
                schedule = sumWeight.divide(totalWeight, 0, BigDecimal.ROUND_UP);
                System.err.println(schedule);
                i.setProjectSchedule(schedule.toString());
            } else {
                i.setProjectSchedule("0");
            }
            // 计算项目进度
//            int collect = companyProjectSchedules.stream().collect(Collectors.summingInt(CompanyProjectSchedule::getSchedule));
//            if (collect > 0 && companyProjectSchedules.size() > 0) {
//                BigDecimal total = new BigDecimal(collect);
//                BigDecimal schedule = total.divide(new BigDecimal(companyProjectSchedules.size()), 0, BigDecimal.ROUND_UP);
//                i.setProjectSchedule(schedule.toString());
//            } else {
//                i.setProjectSchedule("0");
//            }
        });
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param companyProject
     * @return
     */
    @AutoLog(value = "公司项目列表-添加")
    @ApiOperation(value = "公司项目列表-添加", notes = "公司项目列表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody CompanyProject companyProject) {
        companyProjectService.save(companyProject);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param companyProject
     * @return
     */
    @AutoLog(value = "公司项目列表-编辑")
    @ApiOperation(value = "公司项目列表-编辑", notes = "公司项目列表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody CompanyProject companyProject) {
        companyProjectService.updateById(companyProject);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "公司项目列表-通过id删除")
    @ApiOperation(value = "公司项目列表-通过id删除", notes = "公司项目列表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        companyProjectService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "公司项目列表-批量删除")
    @ApiOperation(value = "公司项目列表-批量删除", notes = "公司项目列表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.companyProjectService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "公司项目列表-通过id查询")
    @ApiOperation(value = "公司项目列表-通过id查询", notes = "公司项目列表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        CompanyProject companyProject = companyProjectService.getById(id);
        if (companyProject == null) {
            return Result.error("未找到对应数据");
        }
        // 根据id项目查询图片列表
        List<CompanyProjectImage> companyProjectImages = companyProjectImageService.getProjectById(id);
        companyProject.setCompanyProjectImageList(companyProjectImages);
        return Result.OK(companyProject);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param companyProject
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CompanyProject companyProject) {
        return super.exportXls(request, companyProject, CompanyProject.class, "公司项目列表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CompanyProject.class);
    }

}
