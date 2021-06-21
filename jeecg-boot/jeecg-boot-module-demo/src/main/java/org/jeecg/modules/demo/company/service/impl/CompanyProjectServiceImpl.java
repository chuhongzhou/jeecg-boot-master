package org.jeecg.modules.demo.company.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.demo.company.entity.CompanyProject;
import org.jeecg.modules.demo.company.mapper.CompanyProjectMapper;
import org.jeecg.modules.demo.company.service.ICompanyProjectService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Description: 公司项目列表
 * @Author: jeecg-boot
 * @Date:   2021-06-17
 * @Version: V1.0
 */
@Service
public class CompanyProjectServiceImpl extends ServiceImpl<CompanyProjectMapper, CompanyProject> implements ICompanyProjectService {

    @Resource
    private CompanyProjectMapper companyProjectMapper;

    @Override
    public IPage<CompanyProject> pageByUnitAndType(Page<CompanyProject> page, Map<String,Object> map) {
        return companyProjectMapper.pageByUnitAndType(page, map);
    }

}
