package org.jeecg.modules.inout.aws.vo;

import java.util.List;
import org.jeecg.modules.inout.aws.entity.PqTcReceive;
import org.jeecg.modules.inout.aws.entity.PqTcReceiveSku;
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
 * @Description: 头程到货
 * @Author: jeecg-boot
 * @Date:   2020-11-22
 * @Version: V1.0
 */
@Data
@ApiModel(value="pq_tc_receivePage对象", description="头程到货")
public class PqTcReceivePage {

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
	/**到货日期*/
	@Excel(name = "到货日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "到货日期")
	private java.util.Date receiveTime;
	/**损溢金额*/
	@Excel(name = "损溢金额", width = 15)
	@ApiModelProperty(value = "损溢金额")
	private java.math.BigDecimal lossAmo;
	/**是否确认*/
	@Excel(name = "是否确认", width = 15)
	@ApiModelProperty(value = "是否确认")
	private java.lang.Integer status;
	/**备注*/
	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
	private java.lang.String bz;

	@ExcelCollection(name="到货商品明细")
	@ApiModelProperty(value = "到货商品明细")
	private List<PqTcReceiveSku> pqTcReceiveSkuList;

}
