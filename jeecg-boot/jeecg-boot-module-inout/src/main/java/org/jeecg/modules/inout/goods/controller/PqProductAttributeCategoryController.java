package org.jeecg.modules.inout.goods.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.jeecg.modules.inout.goods.entity.PqProductAttribute;
import org.jeecg.modules.inout.goods.entity.PqProductAttributeCategory;
import org.jeecg.modules.inout.goods.vo.PqProductAttributeCategoryPage;
import org.jeecg.modules.inout.goods.service.IPqProductAttributeCategoryService;
import org.jeecg.modules.inout.goods.service.IPqProductAttributeService;
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
 * @Description: 商品属性分类表
 * @Author: jeecg-boot
 * @Date:   2020-10-31
 * @Version: V1.0
 */
@Api(tags="商品属性分类表")
@RestController
@RequestMapping("/goods/pqProductAttributeCategory")
@Slf4j
public class PqProductAttributeCategoryController {
	@Autowired
	private IPqProductAttributeCategoryService pqProductAttributeCategoryService;
	@Autowired
	private IPqProductAttributeService pqProductAttributeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pqProductAttributeCategory
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "商品属性分类表-分页列表查询")
	@ApiOperation(value="商品属性分类表-分页列表查询", notes="商品属性分类表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PqProductAttributeCategory pqProductAttributeCategory,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PqProductAttributeCategory> queryWrapper = QueryGenerator.initQueryWrapper(pqProductAttributeCategory, req.getParameterMap());
		Page<PqProductAttributeCategory> page = new Page<PqProductAttributeCategory>(pageNo, pageSize);
		IPage<PqProductAttributeCategory> pageList = pqProductAttributeCategoryService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param pqProductAttributeCategoryPage
	 * @return
	 */
	@AutoLog(value = "商品属性分类表-添加")
	@ApiOperation(value="商品属性分类表-添加", notes="商品属性分类表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PqProductAttributeCategoryPage pqProductAttributeCategoryPage) {
		PqProductAttributeCategory pqProductAttributeCategory = new PqProductAttributeCategory();
		BeanUtils.copyProperties(pqProductAttributeCategoryPage, pqProductAttributeCategory);
		pqProductAttributeCategoryService.saveMain(pqProductAttributeCategory, pqProductAttributeCategoryPage.getPqProductAttributeList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pqProductAttributeCategoryPage
	 * @return
	 */
	@AutoLog(value = "商品属性分类表-编辑")
	@ApiOperation(value="商品属性分类表-编辑", notes="商品属性分类表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PqProductAttributeCategoryPage pqProductAttributeCategoryPage) {
		PqProductAttributeCategory pqProductAttributeCategory = new PqProductAttributeCategory();
		BeanUtils.copyProperties(pqProductAttributeCategoryPage, pqProductAttributeCategory);
		PqProductAttributeCategory pqProductAttributeCategoryEntity = pqProductAttributeCategoryService.getById(pqProductAttributeCategory.getId());
		if(pqProductAttributeCategoryEntity==null) {
			return Result.error("未找到对应数据");
		}
		pqProductAttributeCategoryService.updateMain(pqProductAttributeCategory, pqProductAttributeCategoryPage.getPqProductAttributeList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品属性分类表-通过id删除")
	@ApiOperation(value="商品属性分类表-通过id删除", notes="商品属性分类表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pqProductAttributeCategoryService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "商品属性分类表-批量删除")
	@ApiOperation(value="商品属性分类表-批量删除", notes="商品属性分类表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pqProductAttributeCategoryService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品属性分类表-通过id查询")
	@ApiOperation(value="商品属性分类表-通过id查询", notes="商品属性分类表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PqProductAttributeCategory pqProductAttributeCategory = pqProductAttributeCategoryService.getById(id);
		if(pqProductAttributeCategory==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(pqProductAttributeCategory);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品属性参数表通过主表ID查询")
	@ApiOperation(value="商品属性参数表主表ID查询", notes="商品属性参数表-通主表ID查询")
	@GetMapping(value = "/queryPqProductAttributeByMainId")
	public Result<?> queryPqProductAttributeListByMainId(@RequestParam(name="id",required=true) String id) {
		List<PqProductAttribute> pqProductAttributeList = pqProductAttributeService.selectByMainId(id);
		return Result.OK(pqProductAttributeList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param pqProductAttributeCategory
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PqProductAttributeCategory pqProductAttributeCategory) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<PqProductAttributeCategory> queryWrapper = QueryGenerator.initQueryWrapper(pqProductAttributeCategory, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<PqProductAttributeCategory> queryList = pqProductAttributeCategoryService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<PqProductAttributeCategory> pqProductAttributeCategoryList = new ArrayList<PqProductAttributeCategory>();
      if(oConvertUtils.isEmpty(selections)) {
          pqProductAttributeCategoryList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          pqProductAttributeCategoryList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<PqProductAttributeCategoryPage> pageList = new ArrayList<PqProductAttributeCategoryPage>();
      for (PqProductAttributeCategory main : pqProductAttributeCategoryList) {
          PqProductAttributeCategoryPage vo = new PqProductAttributeCategoryPage();
          BeanUtils.copyProperties(main, vo);
          List<PqProductAttribute> pqProductAttributeList = pqProductAttributeService.selectByMainId(main.getId());
          vo.setPqProductAttributeList(pqProductAttributeList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "商品属性分类表列表");
      mv.addObject(NormalExcelConstants.CLASS, PqProductAttributeCategoryPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商品属性分类表数据", "导出人:"+sysUser.getRealname(), "商品属性分类表"));
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
              List<PqProductAttributeCategoryPage> list = ExcelImportUtil.importExcel(file.getInputStream(), PqProductAttributeCategoryPage.class, params);
              for (PqProductAttributeCategoryPage page : list) {
                  PqProductAttributeCategory po = new PqProductAttributeCategory();
                  BeanUtils.copyProperties(page, po);
                  pqProductAttributeCategoryService.saveMain(po, page.getPqProductAttributeList());
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
