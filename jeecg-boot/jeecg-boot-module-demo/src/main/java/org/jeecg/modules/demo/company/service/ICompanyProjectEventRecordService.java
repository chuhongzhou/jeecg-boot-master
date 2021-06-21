package org.jeecg.modules.demo.company.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.company.entity.CompanyProjectEventRecord;
import org.jeecg.modules.demo.company.entity.CompanyProjectSchedule;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 项目关键事件记录
 * @Author: jeecg-boot
 * @Date:   2021-06-17
 * @Version: V1.0
 */
public interface ICompanyProjectEventRecordService extends IService<CompanyProjectEventRecord> {

    // 根据id项目查询图片列表
    List<CompanyProjectEventRecord> getProjectById(String id);

    void insert(CompanyProjectSchedule companyProjectSchedule, HttpServletRequest request, String type);

}
