package com.zygh.webapi.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.zygh.common.CommonResult;
import com.zygh.webapi.service.V2CheckpointEquipmentService;
import io.swagger.annotations.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pojo.V2CheckpointEquipment;
import pojo.V2RoadEquipment;


@RestController
@RequestMapping("/v2-checkpoint-equipment")
@Api(tags = "二期卡口设备(限行)", description = "V2CheckpointEquipmentController")
public class V2CheckpointEquipmentController {

    @Autowired
    V2CheckpointEquipmentService v2CheckpointEquipmentService;

    @ApiOperation("获取设备列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = V2RoadEquipment.class)
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object get(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                      @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {
        return v2CheckpointEquipmentService.findAll(pageNum,pageSize);
    }

    @ApiOperation("按设备名搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "名称", required = true),
    })
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Object search(@RequestBody JSONObject jsonObject) {

        String name = jsonObject.get("name").toString();

        if(StrUtil.isEmpty(name)){
            return new CommonResult().validateFailed("路口名不能为空");
        }

        return v2CheckpointEquipmentService.findByName(name);
    }

    @ApiOperation("按设备编号搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value = "编号", required = true),
    })
    @RequestMapping(value = "/search/code", method = RequestMethod.POST)
    public Object searchCode(@RequestBody JSONObject jsonObject) {

        String code = jsonObject.get("code").toString();

        if(StrUtil.isEmpty(code)){
            return new CommonResult().validateFailed("编号不能为空");
        }

        return v2CheckpointEquipmentService.findByCode(code);
    }

    @ApiOperation("启用/禁用设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true),
            @ApiImplicitParam(name = "valide",value = "启用/禁用(1/0)", required = true),
            @ApiImplicitParam(name = "remark",value = "备注", required = true),
    })
    @RequestMapping(value = "/active", method = RequestMethod.POST)
    public Object active(@RequestBody V2CheckpointEquipment params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("id不能为空");
        }

        if(StrUtil.isEmpty(params.getRemark())){
            return new CommonResult().validateFailed("备注不能为空");
        }

        int res = v2CheckpointEquipmentService.active(params.getId(),params.getValide(),params.getRemark());
        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("启用/禁用错误");
    }


    @ApiOperation("添加设备")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody V2CheckpointEquipment params) {

        if(StrUtil.isEmpty(params.getAssetCode())){
            return new CommonResult().validateFailed("编码不能为空");
        }

        if(StrUtil.isEmpty(params.getAssetName())){
            return new CommonResult().validateFailed("设备名称不能为空");
        }

        if(v2CheckpointEquipmentService.checkExist(params.getAssetCode()) > 0){
            return new CommonResult().validateFailed("设备已经存在");
        }

        int res = v2CheckpointEquipmentService.add(params);
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
    public Object update(@RequestBody V2CheckpointEquipment params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("id不能为空");
        }

        if(StrUtil.isEmpty(params.getAssetCode())){
            return new CommonResult().validateFailed("编码不能为空");
        }

        if(StrUtil.isEmpty(params.getAssetName())){
            return new CommonResult().validateFailed("设备名称不能为空");
        }

        int res = v2CheckpointEquipmentService.update(params);
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
        return v2CheckpointEquipmentService.validTotal();
    }

    @ApiOperation("获取启用设备列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = V2RoadEquipment.class)
    })
    @RequestMapping(value = "/valid", method = RequestMethod.GET)
    public Object getValid(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                           @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {
        return v2CheckpointEquipmentService.findByValid(pageNum,pageSize);
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
                Cell code = row1.getCell(0);
                code.setCellType(CellType.STRING);
                //获取表中第i行，第2列的单元格
                Cell name = row1.getCell(1);
                name.setCellType(CellType.STRING);
                //excel表的第i行，第3列的单元格
                Cell valid = row1.getCell(2);
                //valid.setCellType(CellType.NUMERIC);

                Cell remark = row1.getCell(3);
                if(remark != null){
                    remark.setCellType(CellType.STRING);
                }

                if(code == null){
                    return new CommonResult().validateFailed("备案编号不能为空");
                }

                if(name == null){
                    return new CommonResult().validateFailed("设备名称不能为空");
                }

                if(valid == null){
                    return new CommonResult().validateFailed("启动/关闭参数不能为空");
                }

                System.out.println(code.getStringCellValue());
                System.out.println(name.getStringCellValue());
                System.out.println(valid.getStringCellValue());

                V2CheckpointEquipment v2CheckpointEquipment = new V2CheckpointEquipment();
                v2CheckpointEquipment.setAssetCode(code.getStringCellValue());
                v2CheckpointEquipment.setAssetName(name.getStringCellValue());
                v2CheckpointEquipment.setValide(Integer.valueOf(valid.getStringCellValue()));
                if(remark != null){
                    v2CheckpointEquipment.setRemark(remark.getStringCellValue());
                }

                if(v2CheckpointEquipmentService.checkExist(code.getStringCellValue()) > 0){
                    v2CheckpointEquipmentService.updateFromExcel(v2CheckpointEquipment);
                }else{
                    v2CheckpointEquipmentService.addFromExcel(v2CheckpointEquipment);
                }
            }
        }
        return new CommonResult().success();
    }
}
