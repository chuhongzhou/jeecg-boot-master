package org.jeecg.modules.demo.company.service.impl;

import org.jeecg.modules.demo.company.entity.CompanyProjectImage;
import org.jeecg.modules.demo.company.mapper.CompanyProjectImageMapper;
import org.jeecg.modules.demo.company.service.ICompanyProjectImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 公司项目图片列表
 * @Author: jeecg-boot
 * @Date:   2021-06-17
 * @Version: V1.0
 */
@Service
public class CompanyProjectImageServiceImpl extends ServiceImpl<CompanyProjectImageMapper, CompanyProjectImage> implements ICompanyProjectImageService {

    @Resource
    private CompanyProjectImageMapper companyProjectImageMapper;

    @Override
    public List<CompanyProjectImage> getProjectById(String projectId) {
        return companyProjectImageMapper.getProjectById(projectId);
    }

}
