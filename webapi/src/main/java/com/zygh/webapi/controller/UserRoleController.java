package com.zygh.webapi.controller;

import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.aspect.OperationLog;
import com.zygh.webapi.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.CarBrand;
import pojo.Role;

@RestController
@RequestMapping("/role")
@Api(tags = "用户角色", description = "UserRoleController")
public class UserRoleController {

    @Autowired
    UserRoleService userRoleService;

    @ApiOperation("获取角色列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getRoleList(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                              @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {
        return userRoleService.findAll(pageNum,pageSize);
    }

    @ApiOperation("添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "名称", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody Role params) {

        if(StrUtil.isEmpty(params.getName())){
            return new CommonResult().validateFailed("名称不能为空");
        }

        int res = userRoleService.add(params);
        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("添加错误");
    }

    @ApiOperation("角色更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name",value = "名称", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody Role params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("ID不能为空");
        }

        if(StrUtil.isEmpty(params.getName())){
            return new CommonResult().validateFailed("名称不能为空");
        }

        int res = userRoleService.update(params);

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("更新失败");
    }

    @ApiOperation("角色删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Object remove(@RequestBody Role params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("ID不能为空");
        }

        int res = userRoleService.remove(params.getId());

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("删除失败");
    }
}
