package org.jeecg.modules.demo.company.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.company.entity.CompanyProjectSchedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 公司项目进度
 * @Author: jeecg-boot
 * @Date:   2021-06-17
 * @Version: V1.0
 */
public interface CompanyProjectScheduleMapper extends BaseMapper<CompanyProjectSchedule> {

    List<CompanyProjectSchedule> getProjectById(String projectId);

    IPage<CompanyProjectSchedule> pageByUnitAndType(Page<CompanyProjectSchedule> page, Map map);

}
