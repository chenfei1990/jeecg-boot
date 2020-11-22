package org.jeecg.modules.inout.inventory.service;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
import org.jeecg.modules.online.config.exception.BusinessException;

import java.util.HashMap;
import java.util.Map;

public class OutPackAuditEnhance implements CgformEnhanceJavaInter {
    @Override
    public int execute(String s, Map<String, Object> map) throws BusinessException {
        return 0;
    }

    @Override
    public int execute(String s, JSONObject jsonObject) throws BusinessException {

        try {
            OnlCgformFieldMapper onlCgformFieldMapper = SpringContextUtils.getBean(OnlCgformFieldMapper.class);
            Map<String,Object> params = new HashMap<>();
            String sql = " call out_pack_audit(#{p_id,jdbcType=VARCHAR})";
            params.put("execute_sql_string",sql);
            params.put("p_id",jsonObject.getString("id"));
            onlCgformFieldMapper.executeUpdatetSQL(params);
        } catch ( Exception ex) {
            throw new BusinessException(ex.getCause().getMessage());
        }
        return 0;
    }
}
