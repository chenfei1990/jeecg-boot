package org.jeecg.modules.inout.aws.service;

import org.jeecg.modules.inout.aws.entity.PqTcReceiveSku;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 到货商品明细
 * @Author: jeecg-boot
 * @Date:   2020-11-22
 * @Version: V1.0
 */
public interface IPqTcReceiveSkuService extends IService<PqTcReceiveSku> {

	public List<PqTcReceiveSku> selectByMainId(String mainId);
}
