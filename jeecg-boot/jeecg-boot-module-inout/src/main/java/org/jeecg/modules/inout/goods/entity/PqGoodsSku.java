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
 * @Description: 商品SKU信息
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
@ApiModel(value="pq_goods对象", description="商品信息")
@Data
@TableName("pq_goods_sku")
public class PqGoodsSku implements Serializable {
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
	/**SKU名称*/
	@Excel(name = "SKU名称", width = 15)
	@ApiModelProperty(value = "SKU名称")
	private java.lang.String name;
	/**规格值*/
	@Excel(name = "规格值", width = 15, dictTable = "pq_type_specs_val", dicText = "value", dicCode = "value")
	@ApiModelProperty(value = "规格值")
	private java.lang.String specsVal;
	/**SKU条码*/
	@Excel(name = "SKU条码", width = 15)
	@ApiModelProperty(value = "SKU条码")
	private java.lang.String skuCode;
	/**售价*/
	@Excel(name = "售价", width = 15)
	@ApiModelProperty(value = "售价")
	private java.math.BigDecimal price;
	/**采购价*/
	@Excel(name = "采购价", width = 15)
	@ApiModelProperty(value = "采购价")
	private java.math.BigDecimal inPrice;
	/**图片*/
	@Excel(name = "图片", width = 15)
	@ApiModelProperty(value = "图片")
	private java.lang.String skuPic;
	/**商品ID*/
	@ApiModelProperty(value = "商品ID")
	private java.lang.String pqGoodsId;
	/**商品销售属性*/
	@Excel(name = "商品销售属性", width = 15)
	@ApiModelProperty(value = "商品销售属性")
	private java.lang.String spData;
}
