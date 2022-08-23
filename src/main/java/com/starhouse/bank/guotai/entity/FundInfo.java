package com.starhouse.bank.guotai.entity;

import lombok.Data;

@Data
public class FundInfo {
    private String fundCode;
    private String fundName;
    private String ServiceMode;
    private String fundType;
    private String fundStatus;
    private String establishDate;
    private String valuationEmail;
    private String netDate;
    private String netAssetVal;
    private String retDate;
    private String openStatus;
    private String confDays;
}
