package org.jeecg.modules.inout.inventory.service.impl;

import org.jeecg.modules.inout.inventory.entity.PqCheckReport;
import org.jeecg.modules.inout.inventory.entity.PqCheckReportSp;
import org.jeecg.modules.inout.inventory.mapper.PqCheckReportSpMapper;
import org.jeecg.modules.inout.inventory.mapper.PqCheckReportMapper;
import org.jeecg.modules.inout.inventory.service.IPqCheckReportService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 盘点报告
 * @Author: jeecg-boot
 * @Date:   2020-10-24
 * @Version: V1.0
 */
@Service
public class PqCheckReportServiceImpl extends ServiceImpl<PqCheckReportMapper, PqCheckReport> implements IPqCheckReportService {

	@Autowired
	private PqCheckReportMapper pqCheckReportMapper;
	@Autowired
	private PqCheckReportSpMapper pqCheckReportSpMapper;
	
	@Override
	@Transactional
	public void delMain(String id) {
		pqCheckReportSpMapper.deleteByMainId(id);
		pqCheckReportMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			pqCheckReportSpMapper.deleteByMainId(id.toString());
			pqCheckReportMapper.deleteById(id);
		}
	}
	
}
