package com.zygh.webapi.controller;

import com.zygh.common.CommonResult;
import com.zygh.webapi.aspect.OperationLog;
import com.zygh.webapi.domain.PageRequest;
import com.zygh.webapi.domain.UserDepartmentRequest;
import com.zygh.webapi.service.UserDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("public/department")
@Api(tags = "department", description = "部门管理")
public class UserDepartmentController {

    @Autowired
    UserDepartmentService userDepartmentService;


    @ApiOperation("获取部门列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getList() {
        return userDepartmentService.findAll(1,100);
    }

    @OperationLog(operEvent = "添加部门", operType=1)
    @ApiOperation("添加部门")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addDepartment(@RequestBody UserDepartmentRequest params) {

        if(userDepartmentService.isExist(params.getName().trim())){
            return new CommonResult().validateFailed("部门名已存在");
        }

        int res = userDepartmentService.insert(params.getName().trim());
        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("添加错误");
    }

    @OperationLog(operEvent = "部门名称更新", operType=4)
    @ApiOperation("部门名称更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateDepartment(@RequestBody UserDepartmentRequest params) {

        if(userDepartmentService.isExist(params.getName().trim())){
            return new CommonResult().validateFailed("部门名已存在");
        }

        int res = userDepartmentService.update(params.getName().trim(),params.getId());
        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("添加错误");
    }

    /*
    @ApiOperation("删除部门")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Object removeDepartment(@RequestBody UserDepartmentRequest params) {

        int res = userDepartmentService.remove(params.getId());
        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("删除错误");
    }*/
}
