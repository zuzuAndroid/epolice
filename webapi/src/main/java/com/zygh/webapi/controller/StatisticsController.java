package com.zygh.webapi.controller;

import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.pojo.ExcelData;
import com.zygh.webapi.service.StatisticsService;
import com.zygh.webapi.utils.ExportExcelUtils;
import dto.FirstFilterDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pojo.RoadEquipment;
import pojo.RoadStatistics;
import pojo.RoadStatisticsReport;
import pojo.RoadTotalStatistics;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@Api(tags = "路口统计", description = "StatisticsController")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    List<RoadStatistics> excelList = null;
    List<RoadTotalStatistics> totalExcelList = null;
    List<RoadStatisticsReport> reportExcelList = null;
    String periodName = "";

    @ApiOperation("路口数据统计")
    @RequestMapping(value = "/road", method = RequestMethod.POST)
    public Object roadCount(@RequestBody FirstFilterDto params) {

        if(StrUtil.isEmpty(params.getStartDate())){
            return new CommonResult().validateFailed("请输入开始日期");
        }

        if(StrUtil.isEmpty(params.getEndDate())){
            return new CommonResult().validateFailed("请输入结束日期");
        }

        //int pageNum = params.getPageNum();
        //int pageSize = params.getPageSize();

        if(params.getPeriod() == 0){
            List<RoadStatistics> list = new ArrayList<>();
            List<RoadStatistics> period1 = statisticsService.v1RoadCountNoPage(params);
            List<RoadStatistics> period2 = statisticsService.v2RoadCountNoPage(params);
            list.addAll(period1);
            list.addAll(period2);
            Map<String, Object> data = new HashMap<>(16);
            data.put("list", list);

            periodName = "全部";
            excelList = list;

            return new CommonResult().success(data);
        }else if(params.getPeriod() == 1){
            List<RoadStatistics> period1 = statisticsService.v1RoadCountNoPage(params);
            Map<String, Object> data = new HashMap<>(16);
            data.put("list", period1);

            periodName = "一期";
            excelList = period1;

            return new CommonResult().success(data);
        }

        List<RoadStatistics> period2 = statisticsService.v2RoadCountNoPage(params);
        Map<String, Object> data = new HashMap<>(16);
        data.put("list", period2);

        periodName = "二期";
        excelList = period2;
        return new CommonResult().success(data);
    }

    @ApiOperation("路口数据统计导出Excel")
    @RequestMapping(value = "/road-excel", method = RequestMethod.GET)
    public void roadCountExport(HttpServletResponse response) throws Exception{

        ExcelData data = new ExcelData();
        data.setName("路口数据统计("+periodName+")");
        List<String> titles = new ArrayList();
        titles.add("地点");
        titles.add("违法数量");
        titles.add("维护公司");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        List<Object> row = new ArrayList();

        for(int i = 0;i<excelList.size();i++){
            row = new ArrayList();
            row.add(excelList.get(i).getRoadName());
            row.add(excelList.get(i).getTotal());
            row.add(excelList.get(i).getCompany());
            rows.add(row);
        }

        data.setRows(rows);

        /*File f = new File("路口数据统计.xlsx");
        FileOutputStream out = new FileOutputStream(f);
        ExportExcelUtils.exportExcel(data, out);
        out.close();*/
        ExportExcelUtils.exportExcel(response,"路口数据统计"+periodName+".xlsx",data);
    }

    @ApiOperation("路口汇总统计")
    @RequestMapping(value = "/road-total", method = RequestMethod.POST)
    public Object queryTotal(@RequestBody FirstFilterDto params) {

        if(StrUtil.isEmpty(params.getStartDate())){
            return new CommonResult().validateFailed("请输入开始日期");
        }

        if(StrUtil.isEmpty(params.getEndDate())){
            return new CommonResult().validateFailed("请输入结束日期");
        }

        totalExcelList = statisticsService.roadTotalCount(params);
        return totalExcelList;
    }

    @ApiOperation("路口汇总导出Excel")
    @RequestMapping(value = "/road-total-excel", method = RequestMethod.GET)
    public void roadTotalExport(HttpServletResponse response) throws Exception{

        ExcelData data = new ExcelData();
        data.setName("路口汇总统计");
        List<String> titles = new ArrayList();
        titles.add("地点");
        titles.add("平台抓拍总数(条)");
        titles.add("平台初筛通过(条)");
        titles.add("人工初筛通过(条)");
        titles.add("复核通过(条)");
        titles.add("备注");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        List<Object> row = new ArrayList();
        for(int i = 0;i<totalExcelList.size();i++){
            row = new ArrayList();
            row.add(totalExcelList.get(i).getCjdz());
            row.add(totalExcelList.get(i).getTotal());
            row.add(totalExcelList.get(i).getAlogTotal());
            row.add(totalExcelList.get(i).getPeopleTotal());
            row.add(totalExcelList.get(i).getReviewTotal());
            rows.add(row);
        }

        data.setRows(rows);
        ExportExcelUtils.exportExcel(response,"路口汇总统计.xlsx",data);
    }

    @ApiOperation("路口数据报表")
    @RequestMapping(value = "/road-report", method = RequestMethod.POST)
    public Object queryReport(@RequestBody FirstFilterDto params) {

        if(StrUtil.isEmpty(params.getStartDate())){
            return new CommonResult().validateFailed("请输入开始日期");
        }

        if(StrUtil.isEmpty(params.getEndDate())){
            return new CommonResult().validateFailed("请输入结束日期");
        }

        List<RoadStatisticsReport> list = statisticsService.roadReport(params);

        for(int i = 0;i < list.size();i++){
            list.get(i).setAlogRate(list.get(i).getAlogTotal()/list.get(i).getTotal());
            if(list.get(i).getAlogTotal() > 0) list.get(i).setPeopleRate(list.get(i).getPeopleTotal()/list.get(i).getAlogTotal());
            if(list.get(i).getPeopleTotal() > 0) list.get(i).setReviewRate(list.get(i).getReviewTotal()/list.get(i).getPeopleTotal());
        }
        reportExcelList = list;

        return reportExcelList;
    }

    @ApiOperation("路口数据报表导出Excel")
    @RequestMapping(value = "/road-report-excel", method = RequestMethod.GET)
    public void roadReportExport(HttpServletResponse response) throws Exception{

        ExcelData data = new ExcelData();
        data.setName("路口数据报表");
        List<String> titles = new ArrayList();
        titles.add("地点");
        titles.add("平台抓拍总数(条)");
        titles.add("平台初筛通过(条)");
        titles.add("人工初筛通过(条)");
        titles.add("复核通过(条)");
        titles.add("平台筛选率");
        titles.add("人工初筛率");
        titles.add("复核通过率");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        List<Object> row = new ArrayList();
        for(int i = 0;i<reportExcelList.size();i++){
            row = new ArrayList();
            row.add(reportExcelList.get(i).getCjdz());
            row.add(reportExcelList.get(i).getTotal());
            row.add(reportExcelList.get(i).getAlogTotal());
            row.add(reportExcelList.get(i).getPeopleTotal());
            row.add(reportExcelList.get(i).getReviewTotal());
            row.add(reportExcelList.get(i).getAlogRate());
            row.add(reportExcelList.get(i).getPeopleRate());
            row.add(reportExcelList.get(i).getReviewRate());
            rows.add(row);
        }

        data.setRows(rows);
        ExportExcelUtils.exportExcel(response,"路口数据报表.xlsx",data);
    }
}
