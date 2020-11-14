package org.jeecg.modules.inout.inventory.service;

import org.jeecg.modules.inout.inventory.entity.PqStockOrderSku;
import org.jeecg.modules.inout.inventory.entity.PqStockOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 采购订单
 * @Author: jeecg-boot
 * @Date:   2020-11-14
 * @Version: V1.0
 */
public interface IPqStockOrderService extends IService<PqStockOrder> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(PqStockOrder pqStockOrder,List<PqStockOrderSku> pqStockOrderSkuList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(PqStockOrder pqStockOrder,List<PqStockOrderSku> pqStockOrderSkuList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
