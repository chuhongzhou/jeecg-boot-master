package org.jeecg.modules.demo.company.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.company.entity.CompanyProjectImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 公司项目图片列表
 * @Author: jeecg-boot
 * @Date:   2021-06-17
 * @Version: V1.0
 */
public interface CompanyProjectImageMapper extends BaseMapper<CompanyProjectImage> {

    List<CompanyProjectImage> getProjectById(String projectId);

}
