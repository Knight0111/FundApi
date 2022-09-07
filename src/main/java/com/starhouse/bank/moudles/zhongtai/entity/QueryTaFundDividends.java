package com.starhouse.bank.moudles.zhongtai.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("query_bonus_info")
@Data
public class QueryTaFundDividends {

    @TableField("fund_acco")
    private String fundacco;

    @TableField("cust_name")
    private String custname;

    @TableField(exist = false)
    private String agencyname;

    @TableField("divid_type")
    private String flag;

    @TableField(exist = false)
    private String fundcode;

    @TableField("fund_name")
    private String fundname;

    @TableField(exist = false)
    private String businname;

    @TableField("conf_date")
    private String xdate;

    @TableField(exist = false)
    private String cdate;

    @TableField("reg_date")
    private String regdate;

    @TableField("total_share")
    private String totalshare;

    @TableField("unit_profit")
    private String unitprofit;

    @TableField("total_profit")
    private String totalprofit;

    @TableField("real_balance")
    private String realbalance;

    @TableField("reinvest_balance")
    private String reinvestbalance;

    @TableField("real_shares")
    private String realshares;

    @TableField(exist = false)
    private String tradefare;

    @TableField("last_date")
    private String lastdate;

    @TableField(exist = false)
    private String netvalue;

    @TableField("deduct_balance")
    private String profit;

    @TableField(exist = false)
    private String custtype;

    @TableField(exist = false)
    private String identitytype;

    @TableField(exist = false)
    private String identityno;

    @TableField(exist = false)
    private String tradeacco;

    @TableField(exist = false)
    private String sharetype;

    private String company;


}
