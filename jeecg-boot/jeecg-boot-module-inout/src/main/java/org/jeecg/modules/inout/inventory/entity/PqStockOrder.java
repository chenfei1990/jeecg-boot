package org.jeecg.modules.inout.inventory.entity;

import java.io.Serializable;
import java.util.Date;
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

/**
 * @Description: 采购订单
 * @Author: jeecg-boot
 * @Date:   2020-11-14
 * @Version: V1.0
 */
@ApiModel(value="pq_stock_order对象", description="采购订单")
@Data
@TableName("pq_stock_order")
public class PqStockOrder implements Serializable {
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
	/**批次号*/
	@Excel(name = "批次号", width = 15)
    @ApiModelProperty(value = "批次号")
    private java.lang.String orderNo;
	/**供应商*/
	@Excel(name = "供应商", width = 15)
    @ApiModelProperty(value = "供应商")
    private java.lang.String supplierId;
	/**供应商名称*/
	@Excel(name = "供应商名称", width = 15)
    @ApiModelProperty(value = "供应商名称")
    private java.lang.String supplierName;
	/**库存地点*/
	@Excel(name = "库存地点", width = 15, dictTable = "pq_stroage_location", dicText = "name", dicCode = "id")
    @Dict(dictTable = "pq_stroage_location", dicText = "name", dicCode = "id")
    @ApiModelProperty(value = "库存地点")
    private java.lang.String storage;
	/**采购日期*/
	@Excel(name = "采购日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "采购日期")
    private java.util.Date orderDate;
	/**总金额*/
	@Excel(name = "总金额", width = 15)
    @ApiModelProperty(value = "总金额")
    private java.math.BigDecimal zongAmo;
	/**收货时间*/
	@Excel(name = "收货时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "收货时间")
    private java.util.Date inDate;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.Integer status;
}
