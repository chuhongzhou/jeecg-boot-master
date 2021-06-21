package org.jeecg.modules.demo.company.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.demo.company.entity.CompanyProjectSchedule;
import org.jeecg.modules.demo.company.service.ICompanyProjectEventRecordService;
import org.jeecg.modules.demo.company.service.ICompanyProjectScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

/**
 * @Description: 公司项目进度
 * @Author: jeecg-boot
 * @Date: 2021-06-17
 * @Version: V1.0
 */
@Api(tags = "公司项目进度")
@RestController
@RequestMapping("/company/companyProjectSchedule")
@Slf4j
public class CompanyProjectScheduleController extends JeecgController<CompanyProjectSchedule, ICompanyProjectScheduleService> {
    @Autowired
    private ICompanyProjectScheduleService companyProjectScheduleService;
    @Autowired
    private ICompanyProjectEventRecordService companyProjectEventRecordService;

    /**
     * 分页列表查询
     *
     * @param companyProjectSchedule
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "公司项目进度-分页列表查询")
    @ApiOperation(value = "公司项目进度-分页列表查询", notes = "公司项目进度-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(CompanyProjectSchedule companyProjectSchedule,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        Page<CompanyProjectSchedule> page = new Page<CompanyProjectSchedule>(pageNo, pageSize);
//		QueryWrapper<CompanyProjectSchedule> queryWrapper = QueryGenerator.initQueryWrapper(companyProjectSchedule, req.getParameterMap());
//		IPage<CompanyProjectSchedule> pageList = companyProjectScheduleService.page(page, queryWrapper);
        Map map = com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(companyProjectSchedule), Map.class);
        IPage<CompanyProjectSchedule> pageList = companyProjectScheduleService.pageByUnitAndType(page, map);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param companyProjectSchedule
     * @return
     */
    @AutoLog(value = "公司项目进度-添加")
    @ApiOperation(value = "公司项目进度-添加", notes = "公司项目进度-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody CompanyProjectSchedule companyProjectSchedule, HttpServletRequest request) {
        companyProjectScheduleService.save(companyProjectSchedule);
        // 添加项目进度增加事件记录
        companyProjectEventRecordService.insert(companyProjectSchedule, request, "add");
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param companyProjectSchedule
     * @return
     */
    @AutoLog(value = "公司项目进度-编辑")
    @ApiOperation(value = "公司项目进度-编辑", notes = "公司项目进度-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody CompanyProjectSchedule companyProjectSchedule, HttpServletRequest request) {
        companyProjectScheduleService.updateById(companyProjectSchedule);
        // 添加项目进度增加事件记录
        companyProjectEventRecordService.insert(companyProjectSchedule, request, "edit");
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "公司项目进度-通过id删除")
    @ApiOperation(value = "公司项目进度-通过id删除", notes = "公司项目进度-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        companyProjectScheduleService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "公司项目进度-批量删除")
    @ApiOperation(value = "公司项目进度-批量删除", notes = "公司项目进度-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.companyProjectScheduleService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "公司项目进度-通过id查询")
    @ApiOperation(value = "公司项目进度-通过id查询", notes = "公司项目进度-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        CompanyProjectSchedule companyProjectSchedule = companyProjectScheduleService.getById(id);
        if (companyProjectSchedule == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(companyProjectSchedule);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param companyProjectSchedule
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CompanyProjectSchedule companyProjectSchedule) {
        return super.exportXls(request, companyProjectSchedule, CompanyProjectSchedule.class, "公司项目进度");
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
        return super.importExcel(request, response, CompanyProjectSchedule.class);
    }

}
