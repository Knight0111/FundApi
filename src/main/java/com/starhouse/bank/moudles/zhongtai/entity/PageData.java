package com.starhouse.bank.moudles.zhongtai.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

@Data
public class PageData {
    private Integer total;
    private List<JSONObject> list;

    public PageData(Integer total, List<JSONObject> list) {
        this.total = total;
        this.list = list;
    }
}
