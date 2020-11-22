package org.jeecg.modules.inout.aws.entity;

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
 * @Description: 到货商品明细
 * @Author: jeecg-boot
 * @Date:   2020-11-22
 * @Version: V1.0
 */
@ApiModel(value="pq_tc_receive对象", description="头程到货")
@Data
@TableName("pq_tc_receive_sku")
public class PqTcReceiveSku implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
	private java.lang.String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "创建日期")
	private java.util.Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
	private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "更新日期")
	private java.util.Date updateTime;
	/**所属部门*/
	@ApiModelProperty(value = "所属部门")
	private java.lang.String sysOrgCode;
	/**SKU名称*/
	@Excel(name = "SKU名称", width = 15)
	@ApiModelProperty(value = "SKU名称")
	private java.lang.String skuName;
	/**头程箱号*/
	@Excel(name = "头程箱号", width = 15)
	@ApiModelProperty(value = "头程箱号")
	private java.lang.String mpackNo;
	/**打包箱号*/
	@Excel(name = "打包箱号", width = 15)
	@ApiModelProperty(value = "打包箱号")
	private java.lang.String packNo;
	/**SKU编号*/
	@Excel(name = "SKU编号", width = 15)
	@ApiModelProperty(value = "SKU编号")
	private java.lang.String skuId;
	/**SKU编码*/
	@Excel(name = "SKU编码", width = 15)
	@ApiModelProperty(value = "SKU编码")
	private java.lang.String skuCode;
	/**批次号*/
	@Excel(name = "批次号", width = 15)
	@ApiModelProperty(value = "批次号")
	private java.lang.String orderNo;
	/**出库数量*/
	@Excel(name = "出库数量", width = 15)
	@ApiModelProperty(value = "出库数量")
	private java.math.BigDecimal packNum;
	/**实际到货数*/
	@Excel(name = "实际到货数", width = 15)
	@ApiModelProperty(value = "实际到货数")
	private java.math.BigDecimal receiveNum;
	/**适用站点*/
	@Excel(name = "适用站点", width = 15)
	@ApiModelProperty(value = "适用站点")
	private java.lang.String sites;
	/**适用账号*/
	@Excel(name = "适用账号", width = 15)
	@ApiModelProperty(value = "适用账号")
	private java.lang.String account;
	/**平台SKU*/
	@Excel(name = "平台SKU", width = 15)
	@ApiModelProperty(value = "平台SKU")
	private java.lang.String platSku;
	/**平台asin*/
	@Excel(name = "平台asin", width = 15)
	@ApiModelProperty(value = "平台asin")
	private java.lang.String platAsin;
	/**平台fnsku*/
	@Excel(name = "平台fnsku", width = 15)
	@ApiModelProperty(value = "平台fnsku")
	private java.lang.String platFnsku;
	/**库存地点ID*/
	@Excel(name = "库存地点ID", width = 15)
	@ApiModelProperty(value = "库存地点ID")
	private java.lang.String storage;
	/**备注*/
	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	/**收货单ID*/
	@ApiModelProperty(value = "收货单ID")
	private java.lang.String tcReceiveId;
	/**pq_out_stock表ID*/
	@Excel(name = "pq_out_stock表ID", width = 15)
	@ApiModelProperty(value = "pq_out_stock表ID")
	private java.lang.String outStockId;
}
