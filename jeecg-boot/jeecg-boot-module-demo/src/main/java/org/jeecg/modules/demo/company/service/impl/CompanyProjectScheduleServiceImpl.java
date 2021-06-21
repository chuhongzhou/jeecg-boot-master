package org.jeecg.modules.demo.company.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.demo.company.entity.CompanyProjectSchedule;
import org.jeecg.modules.demo.company.mapper.CompanyProjectScheduleMapper;
import org.jeecg.modules.demo.company.service.ICompanyProjectScheduleService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description: 公司项目进度
 * @Author: jeecg-boot
 * @Date:   2021-06-17
 * @Version: V1.0
 */
@Service
public class CompanyProjectScheduleServiceImpl extends ServiceImpl<CompanyProjectScheduleMapper, CompanyProjectSchedule> implements ICompanyProjectScheduleService {

    @Resource
    private CompanyProjectScheduleMapper companyProjectScheduleMapper;

    @Override
    public List<CompanyProjectSchedule> getProjectById(String projectId) {
        return companyProjectScheduleMapper.getProjectById(projectId);
    }

    @Override
    public IPage<CompanyProjectSchedule> pageByUnitAndType(Page<CompanyProjectSchedule> page, Map map) {
        return companyProjectScheduleMapper.pageByUnitAndType(page, map);
    }

}
