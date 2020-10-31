package org.jeecg.modules.inout.inventory.controller;

import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.inout.inventory.entity.PqCheckReportSp;
import org.jeecg.modules.inout.inventory.entity.PqCheckReport;
import org.jeecg.modules.inout.inventory.service.IPqCheckReportService;
import org.jeecg.modules.inout.inventory.service.IPqCheckReportSpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

 /**
 * @Description: 盘点报告
 * @Author: jeecg-boot
 * @Date:   2020-10-24
 * @Version: V1.0
 */
@Api(tags="盘点报告")
@RestController
@RequestMapping("/inventory/pqCheckReport")
@Slf4j
public class PqCheckReportController extends JeecgController<PqCheckReport, IPqCheckReportService> {

	@Autowired
	private IPqCheckReportService pqCheckReportService;

	@Autowired
	private IPqCheckReportSpService pqCheckReportSpService;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param pqCheckReport
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "盘点报告-分页列表查询")
	@ApiOperation(value="盘点报告-分页列表查询", notes="盘点报告-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PqCheckReport pqCheckReport,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PqCheckReport> queryWrapper = QueryGenerator.initQueryWrapper(pqCheckReport, req.getParameterMap());
		Page<PqCheckReport> page = new Page<PqCheckReport>(pageNo, pageSize);
		IPage<PqCheckReport> pageList = pqCheckReportService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param pqCheckReport
     * @return
     */
    @AutoLog(value = "盘点报告-添加")
    @ApiOperation(value="盘点报告-添加", notes="盘点报告-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody PqCheckReport pqCheckReport) {
        pqCheckReportService.save(pqCheckReport);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param pqCheckReport
     * @return
     */
    @AutoLog(value = "盘点报告-编辑")
    @ApiOperation(value="盘点报告-编辑", notes="盘点报告-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody PqCheckReport pqCheckReport) {
        pqCheckReportService.updateById(pqCheckReport);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "盘点报告-通过id删除")
    @ApiOperation(value="盘点报告-通过id删除", notes="盘点报告-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        pqCheckReportService.delMain(id);
        return Result.OK("删除成功!");
    }

	 /**
	  * 通过id完成录入
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "盘点报告-通过id完成录入")
	 @ApiOperation(value="盘点报告-通过id完成录入", notes="盘点报告-通过id完成录入")
	 @GetMapping(value = "/finishWrite")
	 public Result<?> finishWrite(@RequestParam(name="id",required=true) String id) {
		 //pqCheckReportService.delMain(id);
		 OnlCgformFieldMapper onlCgformFieldMapper = SpringContextUtils.getBean(OnlCgformFieldMapper.class);
		 Map<String,Object> params = new HashMap<>();
		 String sql = " call finish_report_input(#{p_id,jdbcType=VARCHAR})";
		 params.put("execute_sql_string",sql);
		 params.put("p_id",id);
		 onlCgformFieldMapper.executeUpdatetSQL(params);
		 return Result.OK("录入完成!");

	 }

	 /**
	  * 通过id审核
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "盘点报告-通过id审核")
	 @ApiOperation(value="盘点报告-通过id审核", notes="盘点报告-通过id审核")
	 @GetMapping(value = "/audit")
	 public Result<?> audit(@RequestParam(name="id",required=true) String id) {
		 //pqCheckReportService.delMain(id);
		 OnlCgformFieldMapper onlCgformFieldMapper = SpringContextUtils.getBean(OnlCgformFieldMapper.class);
		 Map<String,Object> params = new HashMap<>();
		 String sql = " call audit_report(#{p_id,jdbcType=VARCHAR})";
		 params.put("execute_sql_string",sql);
		 params.put("p_id",id);
		 onlCgformFieldMapper.executeUpdatetSQL(params);
		 return Result.OK("审核完成!");

	 }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "盘点报告-批量删除")
    @ApiOperation(value="盘点报告-批量删除", notes="盘点报告-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.pqCheckReportService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PqCheckReport pqCheckReport) {
        return super.exportXls(request, pqCheckReport, PqCheckReport.class, "盘点报告");
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PqCheckReport.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-盘点商品录入-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	@AutoLog(value = "盘点商品录入-通过主表ID查询")
	@ApiOperation(value="盘点商品录入-通过主表ID查询", notes="盘点商品录入-通过主表ID查询")
	@GetMapping(value = "/listPqCheckReportSpByMainId")
    public Result<?> listPqCheckReportSpByMainId(PqCheckReportSp pqCheckReportSp,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<PqCheckReportSp> queryWrapper = QueryGenerator.initQueryWrapper(pqCheckReportSp, req.getParameterMap());
        Page<PqCheckReportSp> page = new Page<PqCheckReportSp>(pageNo, pageSize);
        IPage<PqCheckReportSp> pageList = pqCheckReportSpService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param pqCheckReportSp
	 * @return
	 */
	@AutoLog(value = "盘点商品录入-添加")
	@ApiOperation(value="盘点商品录入-添加", notes="盘点商品录入-添加")
	@PostMapping(value = "/addPqCheckReportSp")
	public Result<?> addPqCheckReportSp(@RequestBody PqCheckReportSp pqCheckReportSp) {
		pqCheckReportSpService.save(pqCheckReportSp);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param pqCheckReportSp
	 * @return
	 */
	@AutoLog(value = "盘点商品录入-编辑")
	@ApiOperation(value="盘点商品录入-编辑", notes="盘点商品录入-编辑")
	@PutMapping(value = "/editPqCheckReportSp")
	public Result<?> editPqCheckReportSp(@RequestBody PqCheckReportSp pqCheckReportSp) {
		pqCheckReportSpService.updateById(pqCheckReportSp);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "盘点商品录入-通过id删除")
	@ApiOperation(value="盘点商品录入-通过id删除", notes="盘点商品录入-通过id删除")
	@DeleteMapping(value = "/deletePqCheckReportSp")
	public Result<?> deletePqCheckReportSp(@RequestParam(name="id",required=true) String id) {
		pqCheckReportSpService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "盘点商品录入-批量删除")
	@ApiOperation(value="盘点商品录入-批量删除", notes="盘点商品录入-批量删除")
	@DeleteMapping(value = "/deleteBatchPqCheckReportSp")
	public Result<?> deleteBatchPqCheckReportSp(@RequestParam(name="ids",required=true) String ids) {
	    this.pqCheckReportSpService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportPqCheckReportSp")
    public ModelAndView exportPqCheckReportSp(HttpServletRequest request, PqCheckReportSp pqCheckReportSp) {
		 // Step.1 组装查询条件
		 QueryWrapper<PqCheckReportSp> queryWrapper = QueryGenerator.initQueryWrapper(pqCheckReportSp, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<PqCheckReportSp> pageList = pqCheckReportSpService.list(queryWrapper);
		 List<PqCheckReportSp> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 mv.addObject(NormalExcelConstants.FILE_NAME, "盘点商品录入"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, PqCheckReportSp.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("盘点商品录入报表", "导出人:" + sysUser.getRealname(), "盘点商品录入"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importPqCheckReportSp/{mainId}")
    public Result<?> importPqCheckReportSp(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<PqCheckReportSp> list = ExcelImportUtil.importExcel(file.getInputStream(), PqCheckReportSp.class, params);
				 for (PqCheckReportSp temp : list) {
                    temp.setCheckReportId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 pqCheckReportSpService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-盘点商品录入-end----------------------------------------------*/




}
