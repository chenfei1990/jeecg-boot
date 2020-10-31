package org.jeecg.modules.inout.goods.service.impl;

import org.jeecg.modules.inout.goods.entity.PqGoods;
import org.jeecg.modules.inout.goods.entity.PqGoodsSku;
import org.jeecg.modules.inout.goods.entity.PqProductAttributeValue;
import org.jeecg.modules.inout.goods.mapper.PqGoodsSkuMapper;
import org.jeecg.modules.inout.goods.mapper.PqProductAttributeValueMapper;
import org.jeecg.modules.inout.goods.mapper.PqGoodsMapper;
import org.jeecg.modules.inout.goods.service.IPqGoodsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 商品信息
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
@Service
public class PqGoodsServiceImpl extends ServiceImpl<PqGoodsMapper, PqGoods> implements IPqGoodsService {

	@Autowired
	private PqGoodsMapper pqGoodsMapper;
	@Autowired
	private PqGoodsSkuMapper pqGoodsSkuMapper;
	@Autowired
	private PqProductAttributeValueMapper pqProductAttributeValueMapper;
	
	@Override
	@Transactional
	public void saveMain(PqGoods pqGoods, List<PqGoodsSku> pqGoodsSkuList,List<PqProductAttributeValue> pqProductAttributeValueList) {
		pqGoodsMapper.insert(pqGoods);
		if(pqGoodsSkuList!=null && pqGoodsSkuList.size()>0) {
			for(PqGoodsSku entity:pqGoodsSkuList) {
				//外键设置
				entity.setPqGoodsId(pqGoods.getId());
				pqGoodsSkuMapper.insert(entity);
			}
		}
		if(pqProductAttributeValueList!=null && pqProductAttributeValueList.size()>0) {
			for(PqProductAttributeValue entity:pqProductAttributeValueList) {
				//外键设置
				entity.setGoodsId(pqGoods.getId());
				pqProductAttributeValueMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(PqGoods pqGoods,List<PqGoodsSku> pqGoodsSkuList,List<PqProductAttributeValue> pqProductAttributeValueList) {
		pqGoodsMapper.updateById(pqGoods);
		
		//1.先删除子表数据
		pqGoodsSkuMapper.deleteByMainId(pqGoods.getId());
		pqProductAttributeValueMapper.deleteByMainId(pqGoods.getId());
		
		//2.子表数据重新插入
		if(pqGoodsSkuList!=null && pqGoodsSkuList.size()>0) {
			for(PqGoodsSku entity:pqGoodsSkuList) {
				//外键设置
				entity.setPqGoodsId(pqGoods.getId());
				pqGoodsSkuMapper.insert(entity);
			}
		}
		if(pqProductAttributeValueList!=null && pqProductAttributeValueList.size()>0) {
			for(PqProductAttributeValue entity:pqProductAttributeValueList) {
				//外键设置
				entity.setGoodsId(pqGoods.getId());
				pqProductAttributeValueMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		pqGoodsSkuMapper.deleteByMainId(id);
		pqProductAttributeValueMapper.deleteByMainId(id);
		pqGoodsMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			pqGoodsSkuMapper.deleteByMainId(id.toString());
			pqProductAttributeValueMapper.deleteByMainId(id.toString());
			pqGoodsMapper.deleteById(id);
		}
	}
	
}
