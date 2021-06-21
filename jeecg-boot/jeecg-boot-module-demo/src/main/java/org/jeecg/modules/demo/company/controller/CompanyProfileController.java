package org.jeecg.modules.demo.company.controller;

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
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.company.entity.CompanyProfile;
import org.jeecg.modules.demo.company.service.ICompanyProfileService;

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
 * @Description: 公司概况
 * @Author: jeecg-boot
 * @Date:   2021-06-17
 * @Version: V1.0
 */
@Api(tags="公司概况")
@RestController
@RequestMapping("/company/companyProfile")
@Slf4j
public class CompanyProfileController extends JeecgController<CompanyProfile, ICompanyProfileService> {
	@Autowired
	private ICompanyProfileService companyProfileService;

	 /**
	  * 全部列表查询
	  *
	  * @return
	  */
	 @AutoLog(value = "公司概况-全部列表查询")
	 @ApiOperation(value="公司概况-全部列表查询", notes="公司概况-全部列表查询")
	 @GetMapping(value = "/queryList")
	 public Result<?> queryList() {
		 List<CompanyProfile> list = companyProfileService.list();
		 return Result.OK(list);
	 }

	/**
	 * 分页列表查询
	 *
	 * @param companyProfile
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "公司概况-分页列表查询")
	@ApiOperation(value="公司概况-分页列表查询", notes="公司概况-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CompanyProfile companyProfile,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CompanyProfile> queryWrapper = QueryGenerator.initQueryWrapper(companyProfile, req.getParameterMap());
		Page<CompanyProfile> page = new Page<CompanyProfile>(pageNo, pageSize);
		IPage<CompanyProfile> pageList = companyProfileService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param companyProfile
	 * @return
	 */
	@AutoLog(value = "公司概况-添加")
	@ApiOperation(value="公司概况-添加", notes="公司概况-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CompanyProfile companyProfile) {
		companyProfileService.save(companyProfile);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param companyProfile
	 * @return
	 */
	@AutoLog(value = "公司概况-编辑")
	@ApiOperation(value="公司概况-编辑", notes="公司概况-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CompanyProfile companyProfile) {
		companyProfileService.updateById(companyProfile);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "公司概况-通过id删除")
	@ApiOperation(value="公司概况-通过id删除", notes="公司概况-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		companyProfileService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "公司概况-批量删除")
	@ApiOperation(value="公司概况-批量删除", notes="公司概况-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.companyProfileService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "公司概况-通过id查询")
	@ApiOperation(value="公司概况-通过id查询", notes="公司概况-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CompanyProfile companyProfile = companyProfileService.getById(id);
		if(companyProfile==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(companyProfile);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param companyProfile
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CompanyProfile companyProfile) {
        return super.exportXls(request, companyProfile, CompanyProfile.class, "公司概况");
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
        return super.importExcel(request, response, CompanyProfile.class);
    }

}
