package org.jeecg.modules.inout.finance.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.inout.finance.entity.PqPaymethod;
import org.jeecg.modules.inout.finance.service.IPqPaymethodService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 支付方式定义
 * @Author: jeecg-boot
 * @Date:   2020-10-23
 * @Version: V1.0
 */
@Api(tags="支付方式定义")
@RestController
@RequestMapping("/finance/pqPaymethod")
@Slf4j
public class PqPaymethodController extends JeecgController<PqPaymethod, IPqPaymethodService> {
	@Autowired
	private IPqPaymethodService pqPaymethodService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pqPaymethod
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "支付方式定义-分页列表查询")
	@ApiOperation(value="支付方式定义-分页列表查询", notes="支付方式定义-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PqPaymethod pqPaymethod,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PqPaymethod> queryWrapper = QueryGenerator.initQueryWrapper(pqPaymethod, req.getParameterMap());
		Page<PqPaymethod> page = new Page<PqPaymethod>(pageNo, pageSize);
		IPage<PqPaymethod> pageList = pqPaymethodService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param pqPaymethod
	 * @return
	 */
	@AutoLog(value = "支付方式定义-添加")
	@ApiOperation(value="支付方式定义-添加", notes="支付方式定义-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PqPaymethod pqPaymethod) {
		pqPaymethodService.save(pqPaymethod);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pqPaymethod
	 * @return
	 */
	@AutoLog(value = "支付方式定义-编辑")
	@ApiOperation(value="支付方式定义-编辑", notes="支付方式定义-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PqPaymethod pqPaymethod) {
		pqPaymethodService.updateById(pqPaymethod);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "支付方式定义-通过id删除")
	@ApiOperation(value="支付方式定义-通过id删除", notes="支付方式定义-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pqPaymethodService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "支付方式定义-批量删除")
	@ApiOperation(value="支付方式定义-批量删除", notes="支付方式定义-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pqPaymethodService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "支付方式定义-通过id查询")
	@ApiOperation(value="支付方式定义-通过id查询", notes="支付方式定义-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PqPaymethod pqPaymethod = pqPaymethodService.getById(id);
		if(pqPaymethod==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(pqPaymethod);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param pqPaymethod
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PqPaymethod pqPaymethod) {
        return super.exportXls(request, pqPaymethod, PqPaymethod.class, "支付方式定义");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PqPaymethod.class);
    }

}
