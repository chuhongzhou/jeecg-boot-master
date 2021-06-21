package org.jeecg.modules.demo.company.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.company.entity.CompanyProjectEventRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 项目关键事件记录
 * @Author: jeecg-boot
 * @Date:   2021-06-17
 * @Version: V1.0
 */
public interface CompanyProjectEventRecordMapper extends BaseMapper<CompanyProjectEventRecord> {

    List<CompanyProjectEventRecord> getProjectById(String projectId);

}
