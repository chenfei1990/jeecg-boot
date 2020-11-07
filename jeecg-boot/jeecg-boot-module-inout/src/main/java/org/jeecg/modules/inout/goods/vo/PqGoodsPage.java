package org.jeecg.modules.inout.goods.vo;

import java.util.List;
import org.jeecg.modules.inout.goods.entity.PqGoods;
import org.jeecg.modules.inout.goods.entity.PqGoodsSku;
import org.jeecg.modules.inout.goods.entity.PqProductAttributeValue;
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
 * @Description: 商品信息
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
@Data
@ApiModel(value="pq_goodsPage对象", description="商品信息")
public class PqGoodsPage {

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
	/**商品名称*/
	@Excel(name = "商品名称", width = 15)
	@ApiModelProperty(value = "商品名称")
	private java.lang.String name;
	/**简称*/
	@Excel(name = "简称", width = 15)
	@ApiModelProperty(value = "简称")
	private java.lang.String jc;
	/**分类*/
	@Excel(name = "分类", width = 15)
	@ApiModelProperty(value = "分类")
	private java.lang.String goodsTypeId;
	/**进项税率（%）*/
	@Excel(name = "进项税率（%）", width = 15, dictTable = "pq_rate", dicText = "value", dicCode = "value")
    @Dict(dictTable = "pq_rate", dicText = "value", dicCode = "value")
	@ApiModelProperty(value = "进项税率（%）")
	private java.lang.String jxsl;
	/**销项税率（%）*/
	@Excel(name = "销项税率（%）", width = 15, dictTable = "pq_rate", dicText = "value", dicCode = "value")
    @Dict(dictTable = "pq_rate", dicText = "value", dicCode = "value")
	@ApiModelProperty(value = "销项税率（%）")
	private java.lang.String xssl;
	/**关税税率（%）*/
	@Excel(name = "关税税率（%）", width = 15)
	@ApiModelProperty(value = "关税税率（%）")
	private java.lang.Integer gssl;
	/**商品条码*/
	@Excel(name = "商品条码", width = 15)
	@ApiModelProperty(value = "商品条码")
	private java.lang.String barcode;
	/**多规格*/
	@Excel(name = "多规格", width = 15, dicCode = "yn")
    @Dict(dicCode = "yn")
	@ApiModelProperty(value = "多规格")
	private java.lang.String sepcification;
	/**属性分类*/
	@Excel(name = "属性分类", width = 15, dictTable = "pq_product_attribute_category", dicText = "name", dicCode = "id")
    @Dict(dictTable = "pq_product_attribute_category", dicText = "name", dicCode = "id")
	@ApiModelProperty(value = "属性分类")
	private java.lang.String productAttributeCategoryId;
	/**单位*/
	@Excel(name = "单位", width = 15)
	@ApiModelProperty(value = "单位")
	private java.lang.String unit;
	/**税收分类*/
	@Excel(name = "税收分类", width = 15)
	@ApiModelProperty(value = "税收分类")
	private java.lang.String taxId;
	/**产地*/
	@Excel(name = "产地", width = 15)
	@ApiModelProperty(value = "产地")
	private java.lang.String place;
	/**备注*/
	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	/**图片*/
	@Excel(name = "图片", width = 15)
	@ApiModelProperty(value = "图片")
	private java.lang.String pic;

	/**商品状态*/
	@Excel(name = "商品状态", width = 15, dicCode = "pq_goods_stats")
	@Dict(dicCode = "pq_goods_stats")
	@ApiModelProperty(value = "商品状态")
	private java.lang.Integer status;

	@ExcelCollection(name="商品SKU信息")
	@ApiModelProperty(value = "商品SKU信息")
	private List<PqGoodsSku> pqGoodsSkuList;
	@ExcelCollection(name="商品属性参数值")
	@ApiModelProperty(value = "商品属性参数值")
	private List<PqProductAttributeValue> pqProductAttributeValueList;

}
