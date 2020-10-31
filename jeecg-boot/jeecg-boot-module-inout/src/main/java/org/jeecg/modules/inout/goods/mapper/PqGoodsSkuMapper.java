package org.jeecg.modules.inout.goods.mapper;

import java.util.List;
import org.jeecg.modules.inout.goods.entity.PqGoodsSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 商品SKU信息
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
public interface PqGoodsSkuMapper extends BaseMapper<PqGoodsSku> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<PqGoodsSku> selectByMainId(@Param("mainId") String mainId);
}
