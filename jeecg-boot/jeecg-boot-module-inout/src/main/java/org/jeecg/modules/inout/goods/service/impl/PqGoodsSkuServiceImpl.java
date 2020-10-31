package org.jeecg.modules.inout.goods.service.impl;

import org.jeecg.modules.inout.goods.entity.PqGoodsSku;
import org.jeecg.modules.inout.goods.mapper.PqGoodsSkuMapper;
import org.jeecg.modules.inout.goods.service.IPqGoodsSkuService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 商品SKU信息
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
@Service
public class PqGoodsSkuServiceImpl extends ServiceImpl<PqGoodsSkuMapper, PqGoodsSku> implements IPqGoodsSkuService {
	
	@Autowired
	private PqGoodsSkuMapper pqGoodsSkuMapper;
	
	@Override
	public List<PqGoodsSku> selectByMainId(String mainId) {
		return pqGoodsSkuMapper.selectByMainId(mainId);
	}
}
