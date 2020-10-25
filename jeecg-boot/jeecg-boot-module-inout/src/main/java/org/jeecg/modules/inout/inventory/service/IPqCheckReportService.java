package org.jeecg.modules.inout.inventory.service;

import org.jeecg.modules.inout.inventory.entity.PqCheckReportSp;
import org.jeecg.modules.inout.inventory.entity.PqCheckReport;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 盘点报告
 * @Author: jeecg-boot
 * @Date:   2020-10-24
 * @Version: V1.0
 */
public interface IPqCheckReportService extends IService<PqCheckReport> {

	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);


}
