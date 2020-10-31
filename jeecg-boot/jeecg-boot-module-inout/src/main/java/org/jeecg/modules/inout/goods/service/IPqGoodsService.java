package org.jeecg.modules.inout.goods.service;

import org.jeecg.modules.inout.goods.entity.PqGoodsSku;
import org.jeecg.modules.inout.goods.entity.PqProductAttributeValue;
import org.jeecg.modules.inout.goods.entity.PqGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 商品信息
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
public interface IPqGoodsService extends IService<PqGoods> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(PqGoods pqGoods,List<PqGoodsSku> pqGoodsSkuList,List<PqProductAttributeValue> pqProductAttributeValueList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(PqGoods pqGoods,List<PqGoodsSku> pqGoodsSkuList,List<PqProductAttributeValue> pqProductAttributeValueList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
