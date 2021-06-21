package org.jeecg.modules.demo.company.service.impl;

import org.jeecg.modules.demo.company.entity.CompanyProfile;
import org.jeecg.modules.demo.company.mapper.CompanyProfileMapper;
import org.jeecg.modules.demo.company.service.ICompanyProfileService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 公司概况
 * @Author: jeecg-boot
 * @Date:   2021-06-17
 * @Version: V1.0
 */
@Service
public class CompanyProfileServiceImpl extends ServiceImpl<CompanyProfileMapper, CompanyProfile> implements ICompanyProfileService {

}
