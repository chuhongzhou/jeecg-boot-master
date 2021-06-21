package org.jeecg.modules.demo.company.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.company.entity.CompanyProject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 公司项目列表
 * @Author: jeecg-boot
 * @Date: 2021-06-17
 * @Version: V1.0
 */
public interface CompanyProjectMapper extends BaseMapper<CompanyProject> {

    IPage<CompanyProject> pageByUnitAndType(Page<CompanyProject> page, @Param("map") Map<String, Object> map);

}
