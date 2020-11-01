package org.jeecg.modules.inout.goods.service;

import org.jeecg.modules.inout.goods.entity.PqProductAttribute;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 商品属性参数表
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
public interface IPqProductAttributeService extends IService<PqProductAttribute> {

	public List<PqProductAttribute> selectByMainId(String mainId);
}
