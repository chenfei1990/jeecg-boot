package org.jeecg.modules.inout.goods.mapper;

import java.util.List;
import org.jeecg.modules.inout.goods.entity.PqProductAttribute;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 商品属性参数表
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
public interface PqProductAttributeMapper extends BaseMapper<PqProductAttribute> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<PqProductAttribute> selectByMainId(@Param("mainId") String mainId);
}
