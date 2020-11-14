package org.jeecg.modules.inout.inventory.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.inout.inventory.entity.PqStockOrderSku;
import org.jeecg.modules.inout.inventory.entity.PqStockOrder;
import org.jeecg.modules.inout.inventory.vo.PqStockOrderPage;
import org.jeecg.modules.inout.inventory.service.IPqStockOrderService;
import org.jeecg.modules.inout.inventory.service.IPqStockOrderSkuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 采购订单
 * @Author: jeecg-boot
 * @Date:   2020-11-14
 * @Version: V1.0
 */
@Api(tags="采购订单")
@RestController
@RequestMapping("/inventory/pqStockOrder")
@Slf4j
public class PqStockOrderController {
	@Autowired
	private IPqStockOrderService pqStockOrderService;
	@Autowired
	private IPqStockOrderSkuService pqStockOrderSkuService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pqStockOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "采购订单-分页列表查询")
	@ApiOperation(value="采购订单-分页列表查询", notes="采购订单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PqStockOrder pqStockOrder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PqStockOrder> queryWrapper = QueryGenerator.initQueryWrapper(pqStockOrder, req.getParameterMap());
		Page<PqStockOrder> page = new Page<PqStockOrder>(pageNo, pageSize);
		IPage<PqStockOrder> pageList = pqStockOrderService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param pqStockOrderPage
	 * @return
	 */
	@AutoLog(value = "采购订单-添加")
	@ApiOperation(value="采购订单-添加", notes="采购订单-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PqStockOrderPage pqStockOrderPage) {
		PqStockOrder pqStockOrder = new PqStockOrder();
		BeanUtils.copyProperties(pqStockOrderPage, pqStockOrder);
		pqStockOrderService.saveMain(pqStockOrder, pqStockOrderPage.getPqStockOrderSkuList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pqStockOrderPage
	 * @return
	 */
	@AutoLog(value = "采购订单-编辑")
	@ApiOperation(value="采购订单-编辑", notes="采购订单-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PqStockOrderPage pqStockOrderPage) {
		PqStockOrder pqStockOrder = new PqStockOrder();
		BeanUtils.copyProperties(pqStockOrderPage, pqStockOrder);
		PqStockOrder pqStockOrderEntity = pqStockOrderService.getById(pqStockOrder.getId());
		if(pqStockOrderEntity==null) {
			return Result.error("未找到对应数据");
		}
		pqStockOrderService.updateMain(pqStockOrder, pqStockOrderPage.getPqStockOrderSkuList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "采购订单-通过id删除")
	@ApiOperation(value="采购订单-通过id删除", notes="采购订单-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pqStockOrderService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "采购订单-批量删除")
	@ApiOperation(value="采购订单-批量删除", notes="采购订单-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pqStockOrderService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "采购订单-通过id查询")
	@ApiOperation(value="采购订单-通过id查询", notes="采购订单-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PqStockOrder pqStockOrder = pqStockOrderService.getById(id);
		if(pqStockOrder==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(pqStockOrder);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "采购订单商品通过主表ID查询")
	@ApiOperation(value="采购订单商品主表ID查询", notes="采购订单商品-通主表ID查询")
	@GetMapping(value = "/queryPqStockOrderSkuByMainId")
	public Result<?> queryPqStockOrderSkuListByMainId(@RequestParam(name="id",required=true) String id) {
		List<PqStockOrderSku> pqStockOrderSkuList = pqStockOrderSkuService.selectByMainId(id);
		return Result.OK(pqStockOrderSkuList);
	}

	 @AutoLog(value = "采购订单-通过id审核")
	 @ApiOperation(value="采购订单-通过id审核", notes="采购订单-通过id审核")
	 @GetMapping(value = "/audit")
	 public Result<?> audit(@RequestParam(name="id",required=true) String id) {
		 //pqCheckReportService.delMain(id);
		 OnlCgformFieldMapper onlCgformFieldMapper = SpringContextUtils.getBean(OnlCgformFieldMapper.class);
		 Map<String,Object> params = new HashMap<>();
		 String sql = " call stockorder_audit(#{p_id,jdbcType=VARCHAR})";
		 params.put("execute_sql_string",sql);
		 params.put("p_id",id);
		 onlCgformFieldMapper.executeUpdatetSQL(params);
		 return Result.OK("审核完成!");

	 }

    /**
    * 导出excel
    *
    * @param request
    * @param pqStockOrder
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PqStockOrder pqStockOrder) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<PqStockOrder> queryWrapper = QueryGenerator.initQueryWrapper(pqStockOrder, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<PqStockOrder> queryList = pqStockOrderService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<PqStockOrder> pqStockOrderList = new ArrayList<PqStockOrder>();
      if(oConvertUtils.isEmpty(selections)) {
          pqStockOrderList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          pqStockOrderList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<PqStockOrderPage> pageList = new ArrayList<PqStockOrderPage>();
      for (PqStockOrder main : pqStockOrderList) {
          PqStockOrderPage vo = new PqStockOrderPage();
          BeanUtils.copyProperties(main, vo);
          List<PqStockOrderSku> pqStockOrderSkuList = pqStockOrderSkuService.selectByMainId(main.getId());
          vo.setPqStockOrderSkuList(pqStockOrderSkuList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "采购订单列表");
      mv.addObject(NormalExcelConstants.CLASS, PqStockOrderPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("采购订单数据", "导出人:"+sysUser.getRealname(), "采购订单"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
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
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<PqStockOrderPage> list = ExcelImportUtil.importExcel(file.getInputStream(), PqStockOrderPage.class, params);
              for (PqStockOrderPage page : list) {
                  PqStockOrder po = new PqStockOrder();
                  BeanUtils.copyProperties(page, po);
                  pqStockOrderService.saveMain(po, page.getPqStockOrderSkuList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
