package org.jeecg.modules.inout.inventory.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.jeecg.common.aspect.annotation.Dict;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 盘点商品录入
 * @Author: jeecg-boot
 * @Date:   2020-10-24
 * @Version: V1.0
 */
@Data
@TableName("pq_check_report_sp")
@ApiModel(value="pq_check_report对象", description="盘点报告")
public class PqCheckReportSp implements Serializable {
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
	/**库存地点*/
	@Excel(name = "库存地点", width = 15)
    @Dict(dicCode = "id",dicText = "name",dictTable = "pq_stroage_location")
	@ApiModelProperty(value = "库存地点")
	private java.lang.String kcdd;
	/**SKU_ID*/
	@Excel(name = "SKU_ID", width = 15)
	@ApiModelProperty(value = "SKU_ID")
	private java.lang.String skuId;
	/**商品名称*/
	@Excel(name = "商品名称", width = 15)
	@ApiModelProperty(value = "商品名称")
	private java.lang.String skuName;
	/**规格值*/
	@Excel(name = "规格值", width = 15)
	@ApiModelProperty(value = "规格值")
	private java.lang.String specsVal;
	/**账面数量*/
	@Excel(name = "账面数量", width = 15)
	@ApiModelProperty(value = "账面数量")
	private java.math.BigDecimal bookNum;
	/**盘点数量*/
	@Excel(name = "盘点数量", width = 15)
	@ApiModelProperty(value = "盘点数量")
	private java.math.BigDecimal eftNum;
	/**报告ID*/
	@ApiModelProperty(value = "报告ID")
	private java.lang.String checkReportId;
	/**账面金额*/
	@Excel(name = "账面金额", width = 15)
	@ApiModelProperty(value = "账面金额")
	private java.math.BigDecimal bookAmo;
	/**盘点金额*/
	@Excel(name = "盘点金额", width = 15)
	@ApiModelProperty(value = "盘点金额")
	private java.math.BigDecimal eftAmo;
	/**是否损溢*/
	@Excel(name = "是否损溢", width = 15)
	@ApiModelProperty(value = "是否损溢")
	private java.lang.Integer markLoss;
}
