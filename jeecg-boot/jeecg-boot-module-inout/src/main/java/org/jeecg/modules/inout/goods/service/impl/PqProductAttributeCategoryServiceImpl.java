package org.jeecg.modules.inout.goods.service.impl;

import org.jeecg.modules.inout.goods.entity.PqProductAttributeCategory;
import org.jeecg.modules.inout.goods.entity.PqProductAttribute;
import org.jeecg.modules.inout.goods.mapper.PqProductAttributeMapper;
import org.jeecg.modules.inout.goods.mapper.PqProductAttributeCategoryMapper;
import org.jeecg.modules.inout.goods.service.IPqProductAttributeCategoryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 商品属性分类表
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
@Service
public class PqProductAttributeCategoryServiceImpl extends ServiceImpl<PqProductAttributeCategoryMapper, PqProductAttributeCategory> implements IPqProductAttributeCategoryService {

	@Autowired
	private PqProductAttributeCategoryMapper pqProductAttributeCategoryMapper;
	@Autowired
	private PqProductAttributeMapper pqProductAttributeMapper;
	
	@Override
	@Transactional
	public void saveMain(PqProductAttributeCategory pqProductAttributeCategory, List<PqProductAttribute> pqProductAttributeList) {
		pqProductAttributeCategoryMapper.insert(pqProductAttributeCategory);
		if(pqProductAttributeList!=null && pqProductAttributeList.size()>0) {
			for(PqProductAttribute entity:pqProductAttributeList) {
				//外键设置
				entity.setProductAttributeCategoryId(pqProductAttributeCategory.getId());
				pqProductAttributeMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(PqProductAttributeCategory pqProductAttributeCategory,List<PqProductAttribute> pqProductAttributeList) {
		pqProductAttributeCategoryMapper.updateById(pqProductAttributeCategory);
		
		//1.先删除子表数据
		pqProductAttributeMapper.deleteByMainId(pqProductAttributeCategory.getId());
		
		//2.子表数据重新插入
		if(pqProductAttributeList!=null && pqProductAttributeList.size()>0) {
			for(PqProductAttribute entity:pqProductAttributeList) {
				//外键设置
				entity.setProductAttributeCategoryId(pqProductAttributeCategory.getId());
				pqProductAttributeMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		pqProductAttributeMapper.deleteByMainId(id);
		pqProductAttributeCategoryMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			pqProductAttributeMapper.deleteByMainId(id.toString());
			pqProductAttributeCategoryMapper.deleteById(id);
		}
	}
	
}
