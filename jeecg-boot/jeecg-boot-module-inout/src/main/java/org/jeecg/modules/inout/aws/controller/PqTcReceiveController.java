package org.jeecg.modules.inout.aws.controller;

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
import org.jeecg.modules.inout.aws.entity.PqTcReceiveSku;
import org.jeecg.modules.inout.aws.entity.PqTcReceive;
import org.jeecg.modules.inout.aws.vo.PqTcReceivePage;
import org.jeecg.modules.inout.aws.service.IPqTcReceiveService;
import org.jeecg.modules.inout.aws.service.IPqTcReceiveSkuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
 * @Description: 头程到货
 * @Author: jeecg-boot
 * @Date:   2020-11-22
 * @Version: V1.0
 */
@Api(tags="头程到货")
@RestController
@RequestMapping("/aws/pqTcReceive")
@Slf4j
public class PqTcReceiveController {
	@Autowired
	private IPqTcReceiveService pqTcReceiveService;
	@Autowired
	private IPqTcReceiveSkuService pqTcReceiveSkuService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pqTcReceive
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "头程到货-分页列表查询")
	@ApiOperation(value="头程到货-分页列表查询", notes="头程到货-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PqTcReceive pqTcReceive,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PqTcReceive> queryWrapper = QueryGenerator.initQueryWrapper(pqTcReceive, req.getParameterMap());
		Page<PqTcReceive> page = new Page<PqTcReceive>(pageNo, pageSize);
		IPage<PqTcReceive> pageList = pqTcReceiveService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param pqTcReceivePage
	 * @return
	 */
	@AutoLog(value = "头程到货-添加")
	@ApiOperation(value="头程到货-添加", notes="头程到货-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PqTcReceivePage pqTcReceivePage) {
		PqTcReceive pqTcReceive = new PqTcReceive();
		BeanUtils.copyProperties(pqTcReceivePage, pqTcReceive);
		pqTcReceiveService.saveMain(pqTcReceive, pqTcReceivePage.getPqTcReceiveSkuList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pqTcReceivePage
	 * @return
	 */
	@AutoLog(value = "头程到货-编辑")
	@ApiOperation(value="头程到货-编辑", notes="头程到货-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PqTcReceivePage pqTcReceivePage) {
		PqTcReceive pqTcReceive = new PqTcReceive();
		BeanUtils.copyProperties(pqTcReceivePage, pqTcReceive);
		PqTcReceive pqTcReceiveEntity = pqTcReceiveService.getById(pqTcReceive.getId());
		if(pqTcReceiveEntity==null) {
			return Result.error("未找到对应数据");
		}
		pqTcReceiveService.updateMain(pqTcReceive, pqTcReceivePage.getPqTcReceiveSkuList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "头程到货-通过id删除")
	@ApiOperation(value="头程到货-通过id删除", notes="头程到货-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pqTcReceiveService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "头程到货-批量删除")
	@ApiOperation(value="头程到货-批量删除", notes="头程到货-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pqTcReceiveService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "头程到货-通过id查询")
	@ApiOperation(value="头程到货-通过id查询", notes="头程到货-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PqTcReceive pqTcReceive = pqTcReceiveService.getById(id);
		if(pqTcReceive==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(pqTcReceive);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "到货商品明细通过主表ID查询")
	@ApiOperation(value="到货商品明细主表ID查询", notes="到货商品明细-通主表ID查询")
	@GetMapping(value = "/queryPqTcReceiveSkuByMainId")
	public Result<?> queryPqTcReceiveSkuListByMainId(@RequestParam(name="id",required=true) String id) {
		List<PqTcReceiveSku> pqTcReceiveSkuList = pqTcReceiveSkuService.selectByMainId(id);
		return Result.OK(pqTcReceiveSkuList);
	}



	 @AutoLog(value = "头程到货-通过id审核")
	 @ApiOperation(value="头程到货-通过id审核", notes="头程到货-通过id审核")
	 @GetMapping(value = "/audit")
	 @Transactional
	 public Result<?> audit(@RequestParam(name="id",required=true) String id) {

		 OnlCgformFieldMapper onlCgformFieldMapper = SpringContextUtils.getBean(OnlCgformFieldMapper.class);
		 Map<String,Object> params = new HashMap<>();
		 String sql = " call tc_receive_audit(#{p_id,jdbcType=VARCHAR})";
		 params.put("execute_sql_string",sql);
		 params.put("p_id",id);
		 onlCgformFieldMapper.executeUpdatetSQL(params);
		 return Result.OK("确认完成!");

	 }

    /**
    * 导出excel
    *
    * @param request
    * @param pqTcReceive
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PqTcReceive pqTcReceive) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<PqTcReceive> queryWrapper = QueryGenerator.initQueryWrapper(pqTcReceive, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<PqTcReceive> queryList = pqTcReceiveService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<PqTcReceive> pqTcReceiveList = new ArrayList<PqTcReceive>();
      if(oConvertUtils.isEmpty(selections)) {
          pqTcReceiveList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          pqTcReceiveList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<PqTcReceivePage> pageList = new ArrayList<PqTcReceivePage>();
      for (PqTcReceive main : pqTcReceiveList) {
          PqTcReceivePage vo = new PqTcReceivePage();
          BeanUtils.copyProperties(main, vo);
          List<PqTcReceiveSku> pqTcReceiveSkuList = pqTcReceiveSkuService.selectByMainId(main.getId());
          vo.setPqTcReceiveSkuList(pqTcReceiveSkuList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "头程到货列表");
      mv.addObject(NormalExcelConstants.CLASS, PqTcReceivePage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("头程到货数据", "导出人:"+sysUser.getRealname(), "头程到货"));
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
              List<PqTcReceivePage> list = ExcelImportUtil.importExcel(file.getInputStream(), PqTcReceivePage.class, params);
              for (PqTcReceivePage page : list) {
                  PqTcReceive po = new PqTcReceive();
                  BeanUtils.copyProperties(page, po);
                  pqTcReceiveService.saveMain(po, page.getPqTcReceiveSkuList());
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
