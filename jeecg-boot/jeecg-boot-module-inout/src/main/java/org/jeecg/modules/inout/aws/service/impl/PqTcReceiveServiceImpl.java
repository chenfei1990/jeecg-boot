package org.jeecg.modules.inout.aws.service.impl;

import org.jeecg.modules.inout.aws.entity.PqTcReceive;
import org.jeecg.modules.inout.aws.entity.PqTcReceiveSku;
import org.jeecg.modules.inout.aws.mapper.PqTcReceiveSkuMapper;
import org.jeecg.modules.inout.aws.mapper.PqTcReceiveMapper;
import org.jeecg.modules.inout.aws.service.IPqTcReceiveService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 头程到货
 * @Author: jeecg-boot
 * @Date:   2020-11-22
 * @Version: V1.0
 */
@Service
public class PqTcReceiveServiceImpl extends ServiceImpl<PqTcReceiveMapper, PqTcReceive> implements IPqTcReceiveService {

	@Autowired
	private PqTcReceiveMapper pqTcReceiveMapper;
	@Autowired
	private PqTcReceiveSkuMapper pqTcReceiveSkuMapper;
	
	@Override
	@Transactional
	public void saveMain(PqTcReceive pqTcReceive, List<PqTcReceiveSku> pqTcReceiveSkuList) {
		pqTcReceiveMapper.insert(pqTcReceive);
		if(pqTcReceiveSkuList!=null && pqTcReceiveSkuList.size()>0) {
			for(PqTcReceiveSku entity:pqTcReceiveSkuList) {
				//外键设置
				entity.setTcReceiveId(pqTcReceive.getId());
				pqTcReceiveSkuMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(PqTcReceive pqTcReceive,List<PqTcReceiveSku> pqTcReceiveSkuList) {
		pqTcReceiveMapper.updateById(pqTcReceive);
		
		//1.先删除子表数据
		pqTcReceiveSkuMapper.deleteByMainId(pqTcReceive.getId());
		
		//2.子表数据重新插入
		if(pqTcReceiveSkuList!=null && pqTcReceiveSkuList.size()>0) {
			for(PqTcReceiveSku entity:pqTcReceiveSkuList) {
				//外键设置
				entity.setTcReceiveId(pqTcReceive.getId());
				pqTcReceiveSkuMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		pqTcReceiveSkuMapper.deleteByMainId(id);
		pqTcReceiveMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			pqTcReceiveSkuMapper.deleteByMainId(id.toString());
			pqTcReceiveMapper.deleteById(id);
		}
	}
	
}
