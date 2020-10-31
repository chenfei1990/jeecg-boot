package org.jeecg.modules.inout.goods.mapper;

import java.util.List;
import org.jeecg.modules.inout.goods.entity.PqProductAttributeValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 商品属性参数值
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
public interface PqProductAttributeValueMapper extends BaseMapper<PqProductAttributeValue> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<PqProductAttributeValue> selectByMainId(@Param("mainId") String mainId);
}
