package org.jeecg.modules.inout.inventory.entity;

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
 * @Description: 采购订单商品
 * @Author: jeecg-boot
 * @Date:   2020-11-14
 * @Version: V1.0
 */
@ApiModel(value="pq_stock_order对象", description="采购订单")
@Data
@TableName("pq_stock_order_sku")
public class PqStockOrderSku implements Serializable {
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
	/**采购单编号*/
	@ApiModelProperty(value = "采购单编号")
	private java.lang.String stockOrderId;
	/**SKU编号*/
	@Excel(name = "SKU编号", width = 15)
	@ApiModelProperty(value = "SKU编号")
	private java.lang.String skuId;
	/**SKU名称*/
	@Excel(name = "SKU名称", width = 15)
	@ApiModelProperty(value = "SKU名称")
	private java.lang.String skuName;
	/**规格值*/
	@Excel(name = "规格值", width = 15)
	@ApiModelProperty(value = "规格值")
	private java.lang.String specsVal;
	/**采购单价（含税）*/
	@Excel(name = "采购单价（含税）", width = 15)
	@ApiModelProperty(value = "采购单价（含税）")
	private java.math.BigDecimal inPrice;
	/**采购数量*/
	@Excel(name = "采购数量", width = 15)
	@ApiModelProperty(value = "采购数量")
	private java.math.BigDecimal num;
	/**收货数量*/
	@Excel(name = "收货数量", width = 15)
	@ApiModelProperty(value = "收货数量")
	private java.math.BigDecimal inNum;
	/**收货金额*/
	@Excel(name = "收货金额", width = 15)
	@ApiModelProperty(value = "收货金额")
	private java.math.BigDecimal inAmo;
	/**备注*/
	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	/**进项税（%）*/
	@Excel(name = "进项税（%）", width = 15)
	@ApiModelProperty(value = "进项税（%）")
	private java.lang.String jxsl;
	/**结存数量*/
	@Excel(name = "结存数量", width = 15)
	@ApiModelProperty(value = "结存数量")
	private java.lang.String jcNum;
	/**结存金额*/
	@Excel(name = "结存金额", width = 15)
	@ApiModelProperty(value = "结存金额")
	private java.math.BigDecimal jcAmo;
	/**批次编号*/
	@Excel(name = "批次编号", width = 15)
	@ApiModelProperty(value = "批次编号")
	private java.lang.String batchNo;
	/**库存地点*/
	@Excel(name = "库存地点", width = 15)
	@ApiModelProperty(value = "库存地点")
	private java.lang.String kcdd;
	/**出库数量*/
	@Excel(name = "出库数量", width = 15)
	@ApiModelProperty(value = "出库数量")
	private java.math.BigDecimal outNum;
	/**出库金额*/
	@Excel(name = "出库金额", width = 15)
	@ApiModelProperty(value = "出库金额")
	private java.math.BigDecimal outAmo;
	/**退货数量*/
	@Excel(name = "退货数量", width = 15)
	@ApiModelProperty(value = "退货数量")
	private java.math.BigDecimal returnNum;
	/**退货金额*/
	@Excel(name = "退货金额", width = 15)
	@ApiModelProperty(value = "退货金额")
	private java.math.BigDecimal returnAmo;
	/**损溢数量*/
	@Excel(name = "损溢数量", width = 15)
	@ApiModelProperty(value = "损溢数量")
	private java.math.BigDecimal lossNum;
	/**损溢金额*/
	@Excel(name = "损溢金额", width = 15)
	@ApiModelProperty(value = "损溢金额")
	private java.math.BigDecimal lossAmo;
}
