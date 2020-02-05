package com.zygh.webapi.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.zygh.common.CommonResult;
import com.zygh.webapi.pojo.ExcelData;
import com.zygh.webapi.service.V2RoadEquipmentService;
import com.zygh.webapi.utils.ExportExcelUtils;
import io.swagger.annotations.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pojo.CarBrand;
import pojo.RoadEquipment;
import pojo.V2CheckpointEquipment;
import pojo.V2RoadEquipment;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v2-road-equipment")
@Api(tags = "二期电警设备", description = "V2RoadEquipmentController")
public class V2RoadEquipmentController {

    @Autowired
    V2RoadEquipmentService v2RoadEquipmentService;

    @ApiOperation("获取设备列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = V2RoadEquipment.class)
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object get(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                      @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {
        return v2RoadEquipmentService.findAll(pageNum,pageSize);
    }

    @ApiOperation("搜索路口设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "名称", required = true),
    })
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Object search(@RequestBody JSONObject jsonObject) {

        String name = jsonObject.get("name").toString();

        if(StrUtil.isEmpty(name)){
            return new CommonResult().validateFailed("路口名不能为空");
        }

        return v2RoadEquipmentService.search(name);
    }

    @ApiOperation("按编号搜索设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value = "编号", required = true),
    })
    @RequestMapping(value = "/search/code", method = RequestMethod.POST)
    public Object searchCode(@RequestBody JSONObject jsonObject) {

        String code = jsonObject.get("code").toString();

        if(StrUtil.isEmpty(code)){
            return new CommonResult().validateFailed("编号不能为空");
        }

        return v2RoadEquipmentService.findByCode(code);
    }

    @ApiOperation("添加设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "assetCode",value = "编码", required = true),
            @ApiImplicitParam(name = "assetName",value = "名称", required = true),
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody V2RoadEquipment params) {

        if(StrUtil.isEmpty(params.getAssetCode())){
            return new CommonResult().validateFailed("编码不能为空");
        }

        if(StrUtil.isEmpty(params.getAssetName())){
            return new CommonResult().validateFailed("设备名称不能为空");
        }

        if(v2RoadEquipmentService.checkExist(params.getAssetCode()) > 0){
            return new CommonResult().validateFailed("设备已经存在");
        }

        int res = v2RoadEquipmentService.add(params);
        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("添加错误");
    }

    @ApiOperation("启用/禁用设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true),
            @ApiImplicitParam(name = "valide",value = "启用/禁用(1/0)", required = true),
    })
    @RequestMapping(value = "/active", method = RequestMethod.POST)
    public Object active(@RequestBody V2RoadEquipment params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("id不能为空");
        }

        if(StrUtil.isEmpty(params.getRemark())){
            return new CommonResult().validateFailed("备注不能为空");
        }

        int res = v2RoadEquipmentService.active(params.getId(),params.getValide(),params.getRemark());
        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("添加错误");
    }

    @ApiOperation("修改设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "assetCode",value = "编码", required = true),
            @ApiImplicitParam(name = "assetName",value = "名称", required = true),
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody V2RoadEquipment params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("id不能为空");
        }

        if(StrUtil.isEmpty(params.getAssetCode())){
            return new CommonResult().validateFailed("编码不能为空");
        }

        if(StrUtil.isEmpty(params.getAssetName())){
            return new CommonResult().validateFailed("设备名称不能为空");
        }

        int res = v2RoadEquipmentService.update(params);
        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("添加错误");
    }

    @ApiOperation("获取启用设备总数")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = V2RoadEquipment.class)
    })
    @RequestMapping(value = "/valid-total", method = RequestMethod.GET)
    public Object getValidTotal() {
        return v2RoadEquipmentService.validTotal();
    }


    @ApiOperation("获取启用设备列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = V2RoadEquipment.class)
    })
    @RequestMapping(value = "/valid", method = RequestMethod.GET)
    public Object getValid(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                           @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {
        return v2RoadEquipmentService.findByValid(pageNum,pageSize);
    }


    @ApiOperation("上传excel批量导入")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Object upload(MultipartFile file) throws Exception{

        if (file.isEmpty()) {
            return new CommonResult().validateFailed("上传的文件大小为空,请检查");
        }

        //获取文件名称、后缀名、大小
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        System.out.println("文件类型："+suffixName);

        if(!suffixName.equals(".xls") || !suffixName.equals(".xlsx")){
            return new CommonResult().validateFailed("请上传EXCEL文件,请检查");
        }

        //1.得到上传的表
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        //2、获取test工作表
        Sheet sheet = workbook.getSheet("Sheet1");

        if(sheet == null){
            return new CommonResult().validateFailed("请检查Sheet1单元不能为空");
        }

        //获取表的总行数
        int num = sheet.getLastRowNum();
        //System.out.println(num);
        //总列数
        int col = sheet.getRow(0).getLastCellNum();

        System.out.println("总数："+num);

        for (int j = 1; j <= num; j++) {
            Row row1 = sheet.getRow(j);

            if(row1 != null){
                //如果单元格中有数字或者其他格式的数据，则调用setCellType()转换为string类型
                Cell no = row1.getCell(0);
                no.setCellType(CellType.STRING);

                if(no == null){
                    return new CommonResult().validateFailed("唯一编号不能为空");
                }

                Cell code = row1.getCell(1);
                code.setCellType(CellType.STRING);

                if(code == null){
                    return new CommonResult().validateFailed("备案编号不能为空");
                }

                //获取表中第i行，第2列的单元格
                Cell name = row1.getCell(2);
                name.setCellType(CellType.STRING);

                if(name == null){
                    return new CommonResult().validateFailed("设备名称不能为空");
                }

                //excel表的第i行，第3列的单元格
                Cell valid = row1.getCell(3);
                //valid.setCellType(CellType.NUMERIC);

                if(valid == null){
                    return new CommonResult().validateFailed("启动/关闭参数不能为空");
                }

                Cell remark = row1.getCell(4);
                if(remark != null){
                    remark.setCellType(CellType.STRING);
                }

                V2RoadEquipment v2RoadEquipment = new V2RoadEquipment();
                v2RoadEquipment.setAssetNo(no.getStringCellValue());
                v2RoadEquipment.setAssetCode(code.getStringCellValue());
                v2RoadEquipment.setAssetName(name.getStringCellValue());
                v2RoadEquipment.setValide(Integer.valueOf(valid.getStringCellValue()));
                if(remark != null){
                    v2RoadEquipment.setRemark(remark.getStringCellValue());
                }

                if(v2RoadEquipmentService.checkExist(no.getStringCellValue()) > 0){
                    v2RoadEquipmentService.updateFromExcel(v2RoadEquipment);
                }else{
                    v2RoadEquipmentService.addFromExcel(v2RoadEquipment);
                }
            }
        }
        return new CommonResult().success();
    }
}