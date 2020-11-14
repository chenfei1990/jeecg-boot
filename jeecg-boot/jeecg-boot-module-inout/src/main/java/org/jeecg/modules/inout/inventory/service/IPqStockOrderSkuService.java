package org.jeecg.modules.inout.inventory.service;

import org.jeecg.modules.inout.inventory.entity.PqStockOrderSku;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 采购订单商品
 * @Author: jeecg-boot
 * @Date:   2020-11-14
 * @Version: V1.0
 */
public interface IPqStockOrderSkuService extends IService<PqStockOrderSku> {

	public List<PqStockOrderSku> selectByMainId(String mainId);
}
