package org.jeecg.modules.demo.company.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 公司概况
 * @Author: jeecg-boot
 * @Date:   2021-06-17
 * @Version: V1.0
 */
@Data
@TableName("company_profile")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="company_profile对象", description="公司概况")
public class CompanyProfile implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**公司介绍文字内容*/
	@Excel(name = "公司介绍文字内容", width = 15)
    @ApiModelProperty(value = "公司介绍文字内容")
    private String companyContent;
	/**业务范围*/
	@Excel(name = "业务范围", width = 15)
    @ApiModelProperty(value = "业务范围")
    private String scopeBusiness;
	/**项目成果*/
	@Excel(name = "项目成果", width = 15)
    @ApiModelProperty(value = "项目成果")
    private String projectAchievements;
	/**介绍标题*/
	@Excel(name = "介绍标题", width = 15)
    @ApiModelProperty(value = "介绍标题")
    private String companyTitle;
	/**公司背景图*/
	@Excel(name = "公司背景图", width = 15)
    @ApiModelProperty(value = "公司背景图")
    private String companyImage;
}
