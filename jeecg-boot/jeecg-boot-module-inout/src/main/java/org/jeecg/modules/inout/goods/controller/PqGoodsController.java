package org.jeecg.modules.inout.goods.controller;

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
import org.jeecg.modules.inout.goods.entity.PqGoodsSku;
import org.jeecg.modules.inout.goods.entity.PqProductAttributeValue;
import org.jeecg.modules.inout.goods.entity.PqGoods;
import org.jeecg.modules.inout.goods.vo.PqGoodsPage;
import org.jeecg.modules.inout.goods.service.IPqGoodsService;
import org.jeecg.modules.inout.goods.service.IPqGoodsSkuService;
import org.jeecg.modules.inout.goods.service.IPqProductAttributeValueService;
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
 * @Description: 商品信息
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
@Api(tags="商品信息")
@RestController
@RequestMapping("/goods/pqGoods")
@Slf4j
public class PqGoodsController {
	@Autowired
	private IPqGoodsService pqGoodsService;
	@Autowired
	private IPqGoodsSkuService pqGoodsSkuService;
	@Autowired
	private IPqProductAttributeValueService pqProductAttributeValueService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pqGoods
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "商品信息-分页列表查询")
	@ApiOperation(value="商品信息-分页列表查询", notes="商品信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PqGoods pqGoods,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PqGoods> queryWrapper = QueryGenerator.initQueryWrapper(pqGoods, req.getParameterMap());
		Page<PqGoods> page = new Page<PqGoods>(pageNo, pageSize);
		IPage<PqGoods> pageList = pqGoodsService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param pqGoodsPage
	 * @return
	 */
	@AutoLog(value = "商品信息-添加")
	@ApiOperation(value="商品信息-添加", notes="商品信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PqGoodsPage pqGoodsPage) {
		PqGoods pqGoods = new PqGoods();
		BeanUtils.copyProperties(pqGoodsPage, pqGoods);
		pqGoodsService.saveMain(pqGoods, pqGoodsPage.getPqGoodsSkuList(),pqGoodsPage.getPqProductAttributeValueList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pqGoodsPage
	 * @return
	 */
	@AutoLog(value = "商品信息-编辑")
	@ApiOperation(value="商品信息-编辑", notes="商品信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PqGoodsPage pqGoodsPage) {
		PqGoods pqGoods = new PqGoods();
		BeanUtils.copyProperties(pqGoodsPage, pqGoods);
		PqGoods pqGoodsEntity = pqGoodsService.getById(pqGoods.getId());
		if(pqGoodsEntity==null) {
			return Result.error("未找到对应数据");
		}
		pqGoodsService.updateMain(pqGoods, pqGoodsPage.getPqGoodsSkuList(),pqGoodsPage.getPqProductAttributeValueList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品信息-通过id删除")
	@ApiOperation(value="商品信息-通过id删除", notes="商品信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pqGoodsService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "商品信息-批量删除")
	@ApiOperation(value="商品信息-批量删除", notes="商品信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pqGoodsService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品信息-通过id查询")
	@ApiOperation(value="商品信息-通过id查询", notes="商品信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PqGoods pqGoods = pqGoodsService.getById(id);
		if(pqGoods==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(pqGoods);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品SKU信息通过主表ID查询")
	@ApiOperation(value="商品SKU信息主表ID查询", notes="商品SKU信息-通主表ID查询")
	@GetMapping(value = "/queryPqGoodsSkuByMainId")
	public Result<?> queryPqGoodsSkuListByMainId(@RequestParam(name="id",required=true) String id) {
		List<PqGoodsSku> pqGoodsSkuList = pqGoodsSkuService.selectByMainId(id);
		return Result.OK(pqGoodsSkuList);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品属性参数值通过主表ID查询")
	@ApiOperation(value="商品属性参数值主表ID查询", notes="商品属性参数值-通主表ID查询")
	@GetMapping(value = "/queryPqProductAttributeValueByMainId")
	public Result<?> queryPqProductAttributeValueListByMainId(@RequestParam(name="id",required=true) String id) {
		List<PqProductAttributeValue> pqProductAttributeValueList = pqProductAttributeValueService.selectByMainId(id);
		return Result.OK(pqProductAttributeValueList);
	}


	 /**
	  * 通过id审核
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "商品定义-通过id审核")
	 @ApiOperation(value="商品定义-通过id审核", notes="商品定义-通过id审核")
	 @GetMapping(value = "/audit")
	 public Result<?> audit(@RequestParam(name="id",required=true) String id) {
		 //pqCheckReportService.delMain(id);
		 OnlCgformFieldMapper onlCgformFieldMapper = SpringContextUtils.getBean(OnlCgformFieldMapper.class);
		 Map<String,Object> params = new HashMap<>();
		 String sql = " call goods_audit(#{p_id,jdbcType=VARCHAR})";
		 params.put("execute_sql_string",sql);
		 params.put("p_id",id);
		 onlCgformFieldMapper.executeUpdatetSQL(params);
		 return Result.OK("审核完成!");

	 }


	 /**
    * 导出excel
    *
    * @param request
    * @param pqGoods
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PqGoods pqGoods) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<PqGoods> queryWrapper = QueryGenerator.initQueryWrapper(pqGoods, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<PqGoods> queryList = pqGoodsService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<PqGoods> pqGoodsList = new ArrayList<PqGoods>();
      if(oConvertUtils.isEmpty(selections)) {
          pqGoodsList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          pqGoodsList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<PqGoodsPage> pageList = new ArrayList<PqGoodsPage>();
      for (PqGoods main : pqGoodsList) {
          PqGoodsPage vo = new PqGoodsPage();
          BeanUtils.copyProperties(main, vo);
          List<PqGoodsSku> pqGoodsSkuList = pqGoodsSkuService.selectByMainId(main.getId());
          vo.setPqGoodsSkuList(pqGoodsSkuList);
          List<PqProductAttributeValue> pqProductAttributeValueList = pqProductAttributeValueService.selectByMainId(main.getId());
          vo.setPqProductAttributeValueList(pqProductAttributeValueList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "商品信息列表");
      mv.addObject(NormalExcelConstants.CLASS, PqGoodsPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商品信息数据", "导出人:"+sysUser.getRealname(), "商品信息"));
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
              List<PqGoodsPage> list = ExcelImportUtil.importExcel(file.getInputStream(), PqGoodsPage.class, params);
              for (PqGoodsPage page : list) {
                  PqGoods po = new PqGoods();
                  BeanUtils.copyProperties(page, po);
                  pqGoodsService.saveMain(po, page.getPqGoodsSkuList(),page.getPqProductAttributeValueList());
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
