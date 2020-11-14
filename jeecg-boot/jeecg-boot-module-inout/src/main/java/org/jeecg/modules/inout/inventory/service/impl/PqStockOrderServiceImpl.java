package org.jeecg.modules.inout.inventory.service.impl;

import org.jeecg.modules.inout.inventory.entity.PqStockOrder;
import org.jeecg.modules.inout.inventory.entity.PqStockOrderSku;
import org.jeecg.modules.inout.inventory.mapper.PqStockOrderSkuMapper;
import org.jeecg.modules.inout.inventory.mapper.PqStockOrderMapper;
import org.jeecg.modules.inout.inventory.service.IPqStockOrderService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 采购订单
 * @Author: jeecg-boot
 * @Date:   2020-11-14
 * @Version: V1.0
 */
@Service
public class PqStockOrderServiceImpl extends ServiceImpl<PqStockOrderMapper, PqStockOrder> implements IPqStockOrderService {

	@Autowired
	private PqStockOrderMapper pqStockOrderMapper;
	@Autowired
	private PqStockOrderSkuMapper pqStockOrderSkuMapper;
	
	@Override
	@Transactional
	public void saveMain(PqStockOrder pqStockOrder, List<PqStockOrderSku> pqStockOrderSkuList) {
		pqStockOrderMapper.insert(pqStockOrder);
		if(pqStockOrderSkuList!=null && pqStockOrderSkuList.size()>0) {
			for(PqStockOrderSku entity:pqStockOrderSkuList) {
				//外键设置
				entity.setStockOrderId(pqStockOrder.getId());
				pqStockOrderSkuMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(PqStockOrder pqStockOrder,List<PqStockOrderSku> pqStockOrderSkuList) {
		pqStockOrderMapper.updateById(pqStockOrder);
		
		//1.先删除子表数据
		pqStockOrderSkuMapper.deleteByMainId(pqStockOrder.getId());
		
		//2.子表数据重新插入
		if(pqStockOrderSkuList!=null && pqStockOrderSkuList.size()>0) {
			for(PqStockOrderSku entity:pqStockOrderSkuList) {
				//外键设置
				entity.setStockOrderId(pqStockOrder.getId());
				pqStockOrderSkuMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		pqStockOrderSkuMapper.deleteByMainId(id);
		pqStockOrderMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			pqStockOrderSkuMapper.deleteByMainId(id.toString());
			pqStockOrderMapper.deleteById(id);
		}
	}
	
}
