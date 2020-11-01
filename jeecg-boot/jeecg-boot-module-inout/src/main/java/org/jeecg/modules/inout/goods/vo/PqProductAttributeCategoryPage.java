package org.jeecg.modules.inout.goods.vo;

import java.util.List;
import org.jeecg.modules.inout.goods.entity.PqProductAttributeCategory;
import org.jeecg.modules.inout.goods.entity.PqProductAttribute;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 商品属性分类表
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
@Data
@ApiModel(value="pq_product_attribute_categoryPage对象", description="商品属性分类表")
public class PqProductAttributeCategoryPage {

	/**主键*/
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
	/**属性数量*/
	@Excel(name = "属性数量", width = 15)
	@ApiModelProperty(value = "属性数量")
	private java.lang.Integer attributeCount;
	/**参数数量*/
	@Excel(name = "参数数量", width = 15)
	@ApiModelProperty(value = "参数数量")
	private java.lang.Integer paramCount;

	@ExcelCollection(name="商品属性参数表")
	@ApiModelProperty(value = "商品属性参数表")
	private List<PqProductAttribute> pqProductAttributeList;

}
