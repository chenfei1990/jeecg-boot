package org.jeecg.modules.inout.goods.service.impl;

import org.jeecg.modules.inout.goods.entity.PqProductAttribute;
import org.jeecg.modules.inout.goods.mapper.PqProductAttributeMapper;
import org.jeecg.modules.inout.goods.service.IPqProductAttributeService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 商品属性参数表
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
@Service
public class PqProductAttributeServiceImpl extends ServiceImpl<PqProductAttributeMapper, PqProductAttribute> implements IPqProductAttributeService {
	
	@Autowired
	private PqProductAttributeMapper pqProductAttributeMapper;
	
	@Override
	public List<PqProductAttribute> selectByMainId(String mainId) {
		return pqProductAttributeMapper.selectByMainId(mainId);
	}
}
