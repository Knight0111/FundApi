package com.starhouse.bank.moudles.guotai.fundNetVal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("fund_net_val")
@Data
public class FundNetVal {
    private String netDate;
    private String fundCode;
    private String fundName;
    private String assetShares;
    private String assetTotal;
    private String assetNet;
    private String netAssetVal;
    private String totalAssetVal;

    @TableField(exist = false)
    private String fundIncome;

    @TableField(exist = false)
    private String ettFundIncome;

    @TableField(exist = false)
    private String sevenDayRate;

    @TableField(exist = false)
    private String fundLevel;

    private String company;
}
