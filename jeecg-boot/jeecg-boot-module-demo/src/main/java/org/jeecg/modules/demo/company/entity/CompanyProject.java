package org.jeecg.modules.demo.company.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @Description: 公司项目列表
 * @Author: jeecg-boot
 * @Date:   2021-06-17
 * @Version: V1.0
 */
@Data
@TableName("company_project")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="company_project对象", description="公司项目列表")
public class CompanyProject implements Serializable {
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
	/**公司的父id*/
	@Excel(name = "公司的父id", width = 15)
    @ApiModelProperty(value = "公司的父id")
    private String companyId;
	/**项目名称*/
	@Excel(name = "项目名称", width = 15)
    @ApiModelProperty(value = "项目名称")
    private String projectName;
	/**项目类型*/
	@Excel(name = "项目类型", width = 15)
    @ApiModelProperty(value = "项目类型")
    private String projectType;
	/**项目描述*/
	@Excel(name = "项目描述", width = 15)
    @ApiModelProperty(value = "项目描述")
    private String projectDescription;
	/**项目完成进度*/
	@Excel(name = "项目完成进度", width = 15)
    @ApiModelProperty(value = "项目完成进度")
    private String projectSchedule;
	/**项目所在位置（经度）*/
	@Excel(name = "项目所在位置（经度）", width = 15)
    @ApiModelProperty(value = "项目所在位置（经度）")
    private String projectLongitude;
	/**项目所在位置（纬度）*/
	@Excel(name = "项目所在位置（纬度）", width = 15)
    @ApiModelProperty(value = "项目所在位置（纬度）")
    private String projectLatitude;
	/**样式表示*/
	@Excel(name = "样式表示", width = 15)
    @ApiModelProperty(value = "样式表示")
    private String colors;
	/**项目地址*/
	@Excel(name = "项目地址", width = 15)
    @ApiModelProperty(value = "项目地址")
    private String address;

    @TableField(exist=false)
    @ApiModelProperty(value = "项目名称")
    private String companyName;

    @TableField(exist=false)
    @ApiModelProperty(value = "项目对应的图片列表")
    private List<CompanyProjectImage> companyProjectImageList;

    @TableField(exist=false)
    @ApiModelProperty(value = "项目对应的事件列表")
    private List<CompanyProjectEventRecord> companyProjectEventRecords;

    @TableField(exist=false)
    @ApiModelProperty(value = "项目对应的进度列表")
    private List<CompanyProjectSchedule> companyProjectSchedules;

}
