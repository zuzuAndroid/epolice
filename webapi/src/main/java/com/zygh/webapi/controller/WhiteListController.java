package com.zygh.webapi.controller;

import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.service.WhiteListService;
import com.zygh.webapi.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.WhiteList;

import java.util.Date;

@RestController
@RequestMapping("/public/whitelist")
@Api(tags = "中裕广恒白名单（3号库，4号库）", description = "WhiteListController")
public class WhiteListController {

    @Autowired
    WhiteListService whiteListService;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ApiOperation("获取所有")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = WhiteList.class)
    })
    public Object getAll() {
        return whiteListService.findAll();
    }

    @ApiOperation("获取公共列表（3号库）")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = WhiteList.class)
    })
    public Object getPublicList(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                          @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {

        return whiteListService.publicFindAll(pageNum,pageSize);
    }

    @ApiOperation("搜索车牌公共列表")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = WhiteList.class)
    })
    public Object searchLicenseNumber(@RequestParam(defaultValue = "",value = "number") String number,
                                      @RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                                @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {

        if (StrUtil.isEmpty(number)) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        return whiteListService.publicFindByLicenseNumber(number,pageNum,pageSize);
    }

    @ApiOperation("搜索4号库")
    @RequestMapping(value = "/private/search", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = WhiteList.class)
    })
    public Object searchPrivateLicenseNumber(@RequestParam(defaultValue = "",value = "number") String number,
                                      @RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                                      @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {

        if (StrUtil.isEmpty(number)) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        return whiteListService.privateFindByLicenseNumber(number,pageNum,pageSize);
    }

    @ApiOperation("获取私有列表（4号库）")
    @RequestMapping(value = "/zygh", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = WhiteList.class)
    })
    public Object getPrivateList(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                          @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {

        return whiteListService.privateFindAll(pageNum,pageSize);
    }

    @ApiOperation("检查号牌是否已存在")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public Object check(@RequestParam(defaultValue = "",value = "number") String number) {

        if (StrUtil.isEmpty(number)) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        int res = whiteListService.exists(number);

        if(res > 0){
            return new CommonResult().success(true);
        }
        return new CommonResult().success(false);
    }

    @ApiOperation("添加白名单（3号库）")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody WhiteList params) {

        if (StrUtil.isEmpty(params.getLicenseNumber())) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        if (StrUtil.isEmpty(params.getLicenseType())) {
            return new CommonResult().validateFailed("请输入车牌种类");
        }

        if(!DateUtil.isValidDate(params.getStartDate(),"yyyy-MM-dd")){
            return new CommonResult().validateFailed("请输入正确日期格式yyyy-MM-dd");
        }

        if(!DateUtil.isValidDate(params.getEndDate(),"yyyy-MM-dd")){
            return new CommonResult().validateFailed("请输入正确日期格式yyyy-MM-dd");
        }

        if (StrUtil.isEmpty(params.getAddUser())) {
            return new CommonResult().validateFailed("请输入添加人");
        }

        if (StrUtil.isEmpty(params.getRemark())) {
            return new CommonResult().validateFailed("请输入备注");
        }

        if(whiteListService.exists(params.getLicenseNumber()) > 1){
            return new CommonResult().validateFailed("车牌号码已存在");
        }

        params.setCreateDate(new Date());

        int res = whiteListService.add(params);

        if(res > 0){
            return new CommonResult().success();
        }
        return new CommonResult().validateFailed("添加失败");
    }

    @ApiOperation("修改白名单")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody WhiteList params) {

        if (StrUtil.isEmpty(params.getLicenseNumber())) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        if (StrUtil.isEmpty(params.getLicenseType())) {
            return new CommonResult().validateFailed("请输入车牌种类");
        }

        if(!DateUtil.isValidDate(params.getStartDate(),"yyyy-MM-dd")){
            return new CommonResult().validateFailed("请输入正确日期格式yyyy-MM-dd");
        }

        if(!DateUtil.isValidDate(params.getEndDate(),"yyyy-MM-dd")){
            return new CommonResult().validateFailed("请输入正确日期格式yyyy-MM-dd");
        }

        if (StrUtil.isEmpty(params.getAddUser())) {
            return new CommonResult().validateFailed("请输入添加人");
        }

        if (StrUtil.isEmpty(params.getRemark())) {
            return new CommonResult().validateFailed("请输入备注");
        }

        if(whiteListService.exists(params.getLicenseNumber()) > 1){
            return new CommonResult().validateFailed("车牌号码已存在");
        }

        int res = whiteListService.update(params);

        if(res > 0){
            return new CommonResult().success();
        }
        return new CommonResult().validateFailed("添加失败");
    }

    @ApiOperation("检查号牌是否有效")
    @RequestMapping(value = "/check-valide", method = RequestMethod.GET)
    public Object checkForValide(@RequestParam(defaultValue = "",value = "number") String number,
                                 @RequestParam(defaultValue = "",value = "datetime") String datetime) {

        if (StrUtil.isEmpty(number)) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        if (StrUtil.isEmpty(datetime)) {
            return new CommonResult().validateFailed("请输入日期");
        }

        int res = whiteListService.checkForValide(number,datetime);

        if(res > 0){
            return new CommonResult().success(true);
        }
        return new CommonResult().success(false);
    }

    @ApiOperation("删除白名单")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Object remove(@RequestBody WhiteList params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("id不能为空");
        }

        int res = whiteListService.remove(params.getId());

        if(res > 0){
            return new CommonResult().success();
        }
        return new CommonResult().validateFailed("删除失败");
    }

    @ApiOperation("添加白名单（4号库）")
    @RequestMapping(value = "/private/add", method = RequestMethod.POST)
    public Object addToPrivate(@RequestBody WhiteList params) {

        if (StrUtil.isEmpty(params.getLicenseNumber())) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        if (StrUtil.isEmpty(params.getLicenseType())) {
            return new CommonResult().validateFailed("请输入车牌种类");
        }

        if(!DateUtil.isValidDate(params.getStartDate(),"yyyy-MM-dd")){
            return new CommonResult().validateFailed("请输入正确日期格式yyyy-MM-dd");
        }

        if(!DateUtil.isValidDate(params.getEndDate(),"yyyy-MM-dd")){
            return new CommonResult().validateFailed("请输入正确日期格式yyyy-MM-dd");
        }

        if (StrUtil.isEmpty(params.getAddUser())) {
            return new CommonResult().validateFailed("请输入添加人");
        }

        if (StrUtil.isEmpty(params.getRemark())) {
            return new CommonResult().validateFailed("请输入备注");
        }

        if(whiteListService.exists(params.getLicenseNumber()) > 1){
            return new CommonResult().validateFailed("车牌号码已存在");
        }

        params.setCreateDate(new Date());

        int res = whiteListService.addToPrivate(params);

        if(res > 0){
            return new CommonResult().success();
        }
        return new CommonResult().validateFailed("添加失败");
    }

    @ApiOperation("4号库修改")
    @RequestMapping(value = "/private/update", method = RequestMethod.POST)
    public Object updateRight(@RequestBody WhiteList params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("id不能为空");
        }

        if(StrUtil.isEmpty(params.getIllegalType())){
            return new CommonResult().validateFailed("权限不能为空");
        }

        int res = whiteListService.privateUpdate(params);

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("修改失败");
    }
}
