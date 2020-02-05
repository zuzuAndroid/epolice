package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.EvidenceTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.EvidenceType;

import java.util.List;

@Service
public class EvidenceTypeService {

    @Autowired
    EvidenceTypeMapper evidenceTypeMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<EvidenceType> list = evidenceTypeMapper.findAll();

        PageInfo<EvidenceType> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public int update(EvidenceType params){
        return evidenceTypeMapper.update(params);
    }

    public int add(EvidenceType params){
        return evidenceTypeMapper.add(params);
    }

    public int remove(int id){
        return evidenceTypeMapper.remove(id);
    }
}
