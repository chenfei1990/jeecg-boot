package org.jeecg.modules.inout.inventory.service;

import org.jeecg.modules.inout.inventory.entity.PqCheckReportSp;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 盘点商品录入
 * @Author: jeecg-boot
 * @Date:   2020-10-24
 * @Version: V1.0
 */
public interface IPqCheckReportSpService extends IService<PqCheckReportSp> {

	public List<PqCheckReportSp> selectByMainId(String mainId);
}
