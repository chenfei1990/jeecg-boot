package org.jeecg.modules.inout.inventory.service.impl;

import org.jeecg.modules.inout.inventory.entity.PqStockOrderSku;
import org.jeecg.modules.inout.inventory.mapper.PqStockOrderSkuMapper;
import org.jeecg.modules.inout.inventory.service.IPqStockOrderSkuService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 采购订单商品
 * @Author: jeecg-boot
 * @Date:   2020-11-14
 * @Version: V1.0
 */
@Service
public class PqStockOrderSkuServiceImpl extends ServiceImpl<PqStockOrderSkuMapper, PqStockOrderSku> implements IPqStockOrderSkuService {
	
	@Autowired
	private PqStockOrderSkuMapper pqStockOrderSkuMapper;
	
	@Override
	public List<PqStockOrderSku> selectByMainId(String mainId) {
		return pqStockOrderSkuMapper.selectByMainId(mainId);
	}
}
