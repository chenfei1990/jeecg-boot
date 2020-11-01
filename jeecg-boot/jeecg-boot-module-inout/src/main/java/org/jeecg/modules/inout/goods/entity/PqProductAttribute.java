package org.jeecg.modules.inout.goods.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 商品属性参数表
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
@ApiModel(value="pq_product_attribute_category对象", description="商品属性分类表")
@Data
@TableName("pq_product_attribute")
public class PqProductAttribute implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
	private java.lang.String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建日期")
	private java.util.Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
	private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新日期")
	private java.util.Date updateTime;
	/**所属部门*/
	@ApiModelProperty(value = "所属部门")
	private java.lang.String sysOrgCode;
	/**名称*/
	@Excel(name = "名称", width = 15)
	@ApiModelProperty(value = "名称")
	private java.lang.String name;
	/**属性的类型*/
	@Excel(name = "属性的类型", width = 15)
	@ApiModelProperty(value = "属性的类型")
	private java.lang.Integer type;
	/**分类筛选样式*/
	@Excel(name = "分类筛选样式", width = 15)
	@ApiModelProperty(value = "分类筛选样式")
	private java.lang.Integer filterType;
	/**检索类型*/
	@Excel(name = "检索类型", width = 15)
	@ApiModelProperty(value = "检索类型")
	private java.lang.Integer searchType;
	/**商品属性关联*/
	@Excel(name = "商品属性关联", width = 15)
	@ApiModelProperty(value = "商品属性关联")
	private java.lang.Integer relatedStatus;
	/**属性选择类型*/
	@Excel(name = "属性选择类型", width = 15)
	@ApiModelProperty(value = "属性选择类型")
	private java.lang.Integer selectType;
	/**属性值的录入方式*/
	@Excel(name = "属性值的录入方式", width = 15)
	@ApiModelProperty(value = "属性值的录入方式")
	private java.lang.Integer inputType;
	/**可选值列表*/
	@Excel(name = "可选值列表", width = 15)
	@ApiModelProperty(value = "可选值列表")
	private java.lang.String inputList;
	/**是否支持手动新增*/
	@Excel(name = "是否支持手动新增", width = 15)
	@ApiModelProperty(value = "是否支持手动新增")
	private java.lang.Integer handAddStatus;
	/**排序字段*/
	@Excel(name = "排序字段", width = 15)
	@ApiModelProperty(value = "排序字段")
	private java.lang.Integer sort;
	/**商品属性分类id*/
	@ApiModelProperty(value = "商品属性分类id")
	private java.lang.String productAttributeCategoryId;
}
