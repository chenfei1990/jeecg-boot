package org.jeecg.modules.inout.goods.service;

import org.jeecg.modules.inout.goods.entity.PqProductAttribute;
import org.jeecg.modules.inout.goods.entity.PqProductAttributeCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 商品属性分类表
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
public interface IPqProductAttributeCategoryService extends IService<PqProductAttributeCategory> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(PqProductAttributeCategory pqProductAttributeCategory,List<PqProductAttribute> pqProductAttributeList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(PqProductAttributeCategory pqProductAttributeCategory,List<PqProductAttribute> pqProductAttributeList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
