package com.starhouse.bank.moudles.zhongtai.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("confirm_info")
public class QueryTaDataConfirm {
    @TableField(exist = false)
    private String cserialno;

    @TableField("fund_acco")
    private String fundacco;

    @TableField("cust_name")
    private String custname;

    @TableField("agency_name")
    private String agencyname;

    @TableField("busi_flag")
    private String businname;

    @TableField("apply_date")
    private String xdate;

    @TableField("conf_date")
    private String cdate;

    @TableField("fund_code")
    private String fundcode;

    @TableField("fund_name")
    private String fundname;

    @TableField("balance")
    private String balance;

    @TableField("shares")
    private String shares;

    @TableField("conf_balance")
    private String confirmbalance;

    @TableField("conf_shares")
    private String confirmshares;

    @TableField("trade_fare")
    private String tradefare;

    @TableField("regist_fare")
    private String registfare;

    @TableField("fund_fare")
    private String fundfare;

    @TableField("profit_balance")
    private String factdeductbalance;

    @TableField(exist = false)
    private String netvalue;

    @TableField("conf_status")
    private String statusname;

    @TableField(exist = false)
    private String identitytype;

    @TableField(exist = false)
    private String identityno;

    @TableField(exist = false)
    private String tradeacco;

    @TableField(exist = false)
    private String custtypename;

    @TableField("total_fare")
    private String totalfare;

    @TableField("agency_fare")
    private String agencyfare;

    private String company;


}
