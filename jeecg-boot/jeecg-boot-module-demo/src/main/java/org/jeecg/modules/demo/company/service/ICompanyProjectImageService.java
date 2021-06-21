package org.jeecg.modules.demo.company.service;

import org.jeecg.modules.demo.company.entity.CompanyProjectImage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 公司项目图片列表
 * @Author: jeecg-boot
 * @Date:   2021-06-17
 * @Version: V1.0
 */
public interface ICompanyProjectImageService extends IService<CompanyProjectImage> {

    // 根据项目id获取项目的图片列表
    List<CompanyProjectImage> getProjectById(String id);

}
