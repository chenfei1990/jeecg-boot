package org.jeecg.modules.inout.goods.service;

import org.jeecg.modules.inout.goods.entity.PqGoodsSku;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 商品SKU信息
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
public interface IPqGoodsSkuService extends IService<PqGoodsSku> {

	public List<PqGoodsSku> selectByMainId(String mainId);
}
