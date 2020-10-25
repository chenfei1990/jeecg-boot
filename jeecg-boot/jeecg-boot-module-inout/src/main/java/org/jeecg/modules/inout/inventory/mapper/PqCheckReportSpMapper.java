package org.jeecg.modules.inout.inventory.mapper;

import java.util.List;
import org.jeecg.modules.inout.inventory.entity.PqCheckReportSp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 盘点商品录入
 * @Author: jeecg-boot
 * @Date:   2020-10-24
 * @Version: V1.0
 */
public interface PqCheckReportSpMapper extends BaseMapper<PqCheckReportSp> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<PqCheckReportSp> selectByMainId(@Param("mainId") String mainId);

}
