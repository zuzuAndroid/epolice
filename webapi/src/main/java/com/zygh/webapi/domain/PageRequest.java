package com.zygh.webapi.domain;

import lombok.Data;

@Data
public class PageRequest {

    private int pageNum;
    private int pageSize;
}
