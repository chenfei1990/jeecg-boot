package org.jeecg.modules.inout.goods.service.impl;

import org.jeecg.modules.inout.goods.entity.PqProductAttributeValue;
import org.jeecg.modules.inout.goods.mapper.PqProductAttributeValueMapper;
import org.jeecg.modules.inout.goods.service.IPqProductAttributeValueService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 商品属性参数值
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
@Service
public class PqProductAttributeValueServiceImpl extends ServiceImpl<PqProductAttributeValueMapper, PqProductAttributeValue> implements IPqProductAttributeValueService {
	
	@Autowired
	private PqProductAttributeValueMapper pqProductAttributeValueMapper;
	
	@Override
	public List<PqProductAttributeValue> selectByMainId(String mainId) {
		return pqProductAttributeValueMapper.selectByMainId(mainId);
	}
}
