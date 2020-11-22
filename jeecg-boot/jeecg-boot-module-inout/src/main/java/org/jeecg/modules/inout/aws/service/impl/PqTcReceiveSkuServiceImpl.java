package org.jeecg.modules.inout.aws.service.impl;

import org.jeecg.modules.inout.aws.entity.PqTcReceiveSku;
import org.jeecg.modules.inout.aws.mapper.PqTcReceiveSkuMapper;
import org.jeecg.modules.inout.aws.service.IPqTcReceiveSkuService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 到货商品明细
 * @Author: jeecg-boot
 * @Date:   2020-11-22
 * @Version: V1.0
 */
@Service
public class PqTcReceiveSkuServiceImpl extends ServiceImpl<PqTcReceiveSkuMapper, PqTcReceiveSku> implements IPqTcReceiveSkuService {
	
	@Autowired
	private PqTcReceiveSkuMapper pqTcReceiveSkuMapper;
	
	@Override
	public List<PqTcReceiveSku> selectByMainId(String mainId) {
		return pqTcReceiveSkuMapper.selectByMainId(mainId);
	}
}
