package com.starhouse.bank.moudles.zhaoshang.entity;

import lombok.Data;

@Data
public class FundNetVal {
    private String fundCode;
    private String fundName;
    private String navDate;
    private Double assetVol;
    private Double assetNav;
    private Double totalAsset;
    private Double nav;
    private Double accumulativeNav;
    private String remark1;
    private String remark2;
}
