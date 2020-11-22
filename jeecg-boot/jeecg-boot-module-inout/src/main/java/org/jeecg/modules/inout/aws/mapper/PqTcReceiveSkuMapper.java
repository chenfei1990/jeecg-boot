package org.jeecg.modules.inout.aws.mapper;

import java.util.List;
import org.jeecg.modules.inout.aws.entity.PqTcReceiveSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 到货商品明细
 * @Author: jeecg-boot
 * @Date:   2020-11-22
 * @Version: V1.0
 */
public interface PqTcReceiveSkuMapper extends BaseMapper<PqTcReceiveSku> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<PqTcReceiveSku> selectByMainId(@Param("mainId") String mainId);
}
