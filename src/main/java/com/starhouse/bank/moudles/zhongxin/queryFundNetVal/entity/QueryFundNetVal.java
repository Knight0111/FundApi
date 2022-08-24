package com.starhouse.bank.moudles.zhongxin.queryFundNetVal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("fund_net_val")
public class QueryFundNetVal {


    private String netDate;
    private String fundCode;
    private String fundName;
    private String assetShares;
    private String assetNet;

    @TableField("asset_total")
    private String assetValue;
    private String netAssetVal;
    private String totalAssetVal;

    @TableField(exist = false)
    private String checkStatus;

    @TableField(exist = false)
    private String flag;

    @TableField(exist = false)
    private String lastUpdateTime;

    @TableField(exist = false)
    private String recoverAccountFlag;

    private String company;

}
