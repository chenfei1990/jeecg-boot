package org.jeecg.modules.inout.inventory.service.impl;

import org.jeecg.modules.inout.inventory.entity.PqCheckReportSp;
import org.jeecg.modules.inout.inventory.mapper.PqCheckReportSpMapper;
import org.jeecg.modules.inout.inventory.service.IPqCheckReportSpService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 盘点商品录入
 * @Author: jeecg-boot
 * @Date:   2020-10-24
 * @Version: V1.0
 */
@Service
public class PqCheckReportSpServiceImpl extends ServiceImpl<PqCheckReportSpMapper, PqCheckReportSp> implements IPqCheckReportSpService {
	
	@Autowired
	private PqCheckReportSpMapper pqCheckReportSpMapper;
	
	@Override
	public List<PqCheckReportSp> selectByMainId(String mainId) {
		return pqCheckReportSpMapper.selectByMainId(mainId);
	}
}
