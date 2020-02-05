package com.zygh.webapi.controller;

import com.zygh.webapi.pojo.ExcelData;
import com.zygh.webapi.service.V2CheckpointEquipmentService;
import com.zygh.webapi.service.V2IllegalParkingEquipmentService;
import com.zygh.webapi.service.V2RoadEquipmentService;
import com.zygh.webapi.utils.ExportExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pojo.V2CheckpointEquipment;
import pojo.V2IllegalParkingEquipment;
import pojo.V2RoadEquipment;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/public/")
@Api(tags = "导出Excel(电警，限行)", description = "V2CheckpointEquipmentController")
public class ExportExcelController {

    @Autowired
    V2CheckpointEquipmentService v2CheckpointEquipmentService;

    @Autowired
    V2RoadEquipmentService v2RoadEquipmentService;

    @Autowired
    V2IllegalParkingEquipmentService v2IllegalParkingEquipmentService;

    @ApiOperation("导出二期卡口全部设备Excel")
    @RequestMapping(value = "/checkpoint/all-excel", method = RequestMethod.GET)
    public void checkpointExportAll(HttpServletResponse response) throws Exception{

        List<V2CheckpointEquipment> excelList = v2CheckpointEquipmentService.findAll();

        ExcelData data = new ExcelData();
        data.setName("二期卡口全部设备");
        List<String> titles = new ArrayList();
        titles.add("设备编号");
        titles.add("设备名称");
        titles.add("方向");
        titles.add("IP");
        titles.add("维护公司");
        titles.add("是否启用");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        List<Object> row = new ArrayList();

        for(int i = 0;i<excelList.size();i++){
            row = new ArrayList();
            row.add(excelList.get(i).getAssetCode());
            row.add(excelList.get(i).getAssetName());
            row.add(excelList.get(i).getDirection());
            row.add(excelList.get(i).getAssetIp());
            row.add(excelList.get(i).getCompany());
            row.add(excelList.get(i).getValide());
            rows.add(row);
        }

        data.setRows(rows);

        /*File f = new File("路口数据统计.xlsx");
        FileOutputStream out = new FileOutputStream(f);
        ExportExcelUtils.exportExcel(data, out);
        out.close();*/
        ExportExcelUtils.exportExcel(response,"二期卡口全部设备.xlsx",data);

    }

    @ApiOperation("导出已启用二期卡口设备Excel")
    @RequestMapping(value = "/checkpoint/excel", method = RequestMethod.GET)
    public void checkpointExport(HttpServletResponse response) throws Exception{
        List<V2CheckpointEquipment> excelList = v2CheckpointEquipmentService.findByValidNoPage();

        ExcelData data = new ExcelData();
        data.setName("二期卡口启用设备");
        List<String> titles = new ArrayList();
        titles.add("设备编号");
        titles.add("设备名称");
        titles.add("方向");
        titles.add("IP");
        titles.add("维护公司");
        titles.add("是否启用");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        List<Object> row = new ArrayList();

        for(int i = 0;i<excelList.size();i++){
            row = new ArrayList();
            row.add(excelList.get(i).getAssetCode());
            row.add(excelList.get(i).getAssetName());
            row.add(excelList.get(i).getDirection());
            row.add(excelList.get(i).getAssetIp());
            row.add(excelList.get(i).getCompany());
            row.add(excelList.get(i).getValide());
            rows.add(row);
        }

        data.setRows(rows);

        /*File f = new File("路口数据统计.xlsx");
        FileOutputStream out = new FileOutputStream(f);
        ExportExcelUtils.exportExcel(data, out);
        out.close();*/
        ExportExcelUtils.exportExcel(response,"二期卡口启用设备.xlsx",data);

    }

    @ApiOperation("导出全部二期电警设备Excel")
    @RequestMapping(value = "/road/all-excel", method = RequestMethod.GET)
    public void exportAll(HttpServletResponse response) throws Exception{

        List<V2RoadEquipment> excelList = v2RoadEquipmentService.findAllNoPage();

        ExcelData data = new ExcelData();
        data.setName("二期电警全部设备");
        List<String> titles = new ArrayList();
        titles.add("设备编号");
        titles.add("设备名称");
        titles.add("设备类型");
        titles.add("IP");
        titles.add("维护公司");
        titles.add("是否启用");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        List<Object> row = new ArrayList();

        for(int i = 0;i<excelList.size();i++){
            row = new ArrayList();
            row.add(excelList.get(i).getAssetCode());
            row.add(excelList.get(i).getAssetName());
            row.add(excelList.get(i).getAssetType());
            row.add(excelList.get(i).getAssetIp());
            row.add(excelList.get(i).getCompany());
            row.add(excelList.get(i).getValide());
            rows.add(row);
        }

        data.setRows(rows);

        /*File f = new File("路口数据统计.xlsx");
        FileOutputStream out = new FileOutputStream(f);
        ExportExcelUtils.exportExcel(data, out);
        out.close();*/
        ExportExcelUtils.exportExcel(response,"二期电警全部设备.xlsx",data);

    }

    @ApiOperation("导出已启用二期电警设备Excel")
    @RequestMapping(value = "/road/excel", method = RequestMethod.GET)
    public void export(HttpServletResponse response) throws Exception{
        List<V2RoadEquipment> excelList = v2RoadEquipmentService.findByValidNoPage();

        ExcelData data = new ExcelData();
        data.setName("二期电警启用设备");
        List<String> titles = new ArrayList();
        titles.add("设备编号");
        titles.add("设备名称");
        titles.add("设备类型");
        titles.add("IP");
        titles.add("维护公司");
        titles.add("是否启用");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        List<Object> row = new ArrayList();

        for(int i = 0;i<excelList.size();i++){
            row = new ArrayList();
            row.add(excelList.get(i).getAssetCode());
            row.add(excelList.get(i).getAssetName());
            row.add(excelList.get(i).getAssetType());
            row.add(excelList.get(i).getAssetIp());
            row.add(excelList.get(i).getCompany());
            row.add(excelList.get(i).getValide());
            rows.add(row);
        }

        data.setRows(rows);

        /*File f = new File("路口数据统计.xlsx");
        FileOutputStream out = new FileOutputStream(f);
        ExportExcelUtils.exportExcel(data, out);
        out.close();*/
        ExportExcelUtils.exportExcel(response,"二期电警启用设备.xlsx",data);
    }


    @ApiOperation("导出二期违停全部设备Excel")
    @RequestMapping(value = "/parking/all-excel", method = RequestMethod.GET)
    public void parkingExportAll(HttpServletResponse response) throws Exception{

        List<V2IllegalParkingEquipment> excelList = v2IllegalParkingEquipmentService.findAllNoPage();

        ExcelData data = new ExcelData();
        data.setName("二期违停全部设备");
        List<String> titles = new ArrayList();
        titles.add("设备编号");
        titles.add("设备名称");
        titles.add("是否启用");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        List<Object> row = new ArrayList();

        for(int i = 0;i<excelList.size();i++){
            row = new ArrayList();
            row.add(excelList.get(i).getCode());
            row.add(excelList.get(i).getName());
            row.add(excelList.get(i).getValide());
            rows.add(row);
        }

        data.setRows(rows);

        ExportExcelUtils.exportExcel(response,"二期违停全部设备.xlsx",data);
    }

    @ApiOperation("导出已启用二期违停设备Excel")
    @RequestMapping(value = "/parking/excel", method = RequestMethod.GET)
    public void parkingExport(HttpServletResponse response) throws Exception{
        List<V2IllegalParkingEquipment> excelList = v2IllegalParkingEquipmentService.findAllValidNoPage();

        ExcelData data = new ExcelData();
        data.setName("二期违停启用设备");
        List<String> titles = new ArrayList();
        titles.add("设备编号");
        titles.add("设备名称");
        titles.add("是否启用");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        List<Object> row = new ArrayList();

        for(int i = 0;i<excelList.size();i++){
            row = new ArrayList();
            row.add(excelList.get(i).getCode());
            row.add(excelList.get(i).getName());
            row.add(excelList.get(i).getValide());
            rows.add(row);
        }

        data.setRows(rows);

        ExportExcelUtils.exportExcel(response,"二期违停启用设备.xlsx",data);

    }
}
