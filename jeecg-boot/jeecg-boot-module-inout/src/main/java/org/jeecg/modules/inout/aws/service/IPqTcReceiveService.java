package org.jeecg.modules.inout.aws.service;

import org.jeecg.modules.inout.aws.entity.PqTcReceiveSku;
import org.jeecg.modules.inout.aws.entity.PqTcReceive;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 头程到货
 * @Author: jeecg-boot
 * @Date:   2020-11-22
 * @Version: V1.0
 */
public interface IPqTcReceiveService extends IService<PqTcReceive> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(PqTcReceive pqTcReceive,List<PqTcReceiveSku> pqTcReceiveSkuList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(PqTcReceive pqTcReceive,List<PqTcReceiveSku> pqTcReceiveSkuList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
