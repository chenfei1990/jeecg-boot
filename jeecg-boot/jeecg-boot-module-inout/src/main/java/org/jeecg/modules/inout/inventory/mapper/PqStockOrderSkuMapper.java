package org.jeecg.modules.inout.inventory.mapper;

import java.util.List;
import org.jeecg.modules.inout.inventory.entity.PqStockOrderSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 采购订单商品
 * @Author: jeecg-boot
 * @Date:   2020-11-14
 * @Version: V1.0
 */
public interface PqStockOrderSkuMapper extends BaseMapper<PqStockOrderSku> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<PqStockOrderSku> selectByMainId(@Param("mainId") String mainId);
}
