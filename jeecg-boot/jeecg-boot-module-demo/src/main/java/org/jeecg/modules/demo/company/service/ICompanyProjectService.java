package org.jeecg.modules.demo.company.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.company.entity.CompanyProject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Description: 公司项目列表
 * @Author: jeecg-boot
 * @Date:   2021-06-17
 * @Version: V1.0
 */
public interface ICompanyProjectService extends IService<CompanyProject> {

    IPage<CompanyProject> pageByUnitAndType(Page<CompanyProject> page, Map<String,Object> map);

}
