package org.jeecg.modules.demo.company.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.demo.company.entity.CompanyProjectEventRecord;
import org.jeecg.modules.demo.company.entity.CompanyProjectSchedule;
import org.jeecg.modules.demo.company.mapper.CompanyProjectEventRecordMapper;
import org.jeecg.modules.demo.company.service.ICompanyProjectEventRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Description: 项目关键事件记录
 * @Author: jeecg-boot
 * @Date: 2021-06-17
 * @Version: V1.0
 */
@Service
public class CompanyProjectEventRecordServiceImpl extends ServiceImpl<CompanyProjectEventRecordMapper, CompanyProjectEventRecord> implements ICompanyProjectEventRecordService {

    @Resource
    private CompanyProjectEventRecordMapper companyProjectEventRecordMapper;

    @Override
    public List<CompanyProjectEventRecord> getProjectById(String projectId) {
        return companyProjectEventRecordMapper.getProjectById(projectId);
    }

    @Override
    public void insert(CompanyProjectSchedule companyProjectSchedule, HttpServletRequest request, String type) {
        CompanyProjectEventRecord companyProjectEventRecord = new CompanyProjectEventRecord();
        companyProjectEventRecord.setProjectId(companyProjectSchedule.getProjectId());
        String name = JwtUtil.getUserNameByToken(request);
        if (type.equals("add")) {
            companyProjectEventRecord.setCreateBy(name);
            companyProjectEventRecord.setCreateTime(new Date());
            companyProjectEventRecord.setContent(name + "添加了" + companyProjectSchedule.getTitle() + ", 项目进度为:" + companyProjectSchedule.getSchedule());
        } else {
            companyProjectEventRecord.setUpdateBy(JwtUtil.getUserNameByToken(request));
            companyProjectEventRecord.setUpdateTime(new Date());
            companyProjectEventRecord.setContent(name + "编辑了" + companyProjectSchedule.getTitle() + ", 项目进度为:" + companyProjectSchedule.getSchedule());
        }
        companyProjectEventRecordMapper.insert(companyProjectEventRecord);
    }

}
