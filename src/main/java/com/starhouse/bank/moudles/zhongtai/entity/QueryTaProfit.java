package com.starhouse.bank.moudles.zhongtai.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("fund_profit")
@Data
public class QueryTaProfit {

    @TableField("fund_acco")
    private String fundacco;

    @TableField("cust_name")
    private String custname;

    @TableField("cust_type")
    private String custtype;

    @TableField(exist = false)
    private String agencyname;

    @TableField("fund_code")
    private String fundcode;

    @TableField("fund_name")
    private String fundname;

    @TableField("busi_type")
    private String businname;

    @TableField("d_date")
    private String xdate;

    @TableField("conf_date")
    private String cdate;

    @TableField("regist_date")
    private String registdate;

    @TableField("lhold")
    private String holddays;

    @TableField("shares")
    private String confshares;

    @TableField("ori_nav")
    private String beginnetvalue;

    @TableField(exist = false)
    private String begintotalnetvalue;

    @TableField("nav")
    private String netvalue;

    @TableField("total_nav")
    private String totalnetvalue;

    @TableField("curr_ratio")
    private String curratio;

    @TableField("year_ratio")
    private String yearratio;

    @TableField("ori_balance")
    private String deductbalance;

    @TableField("fact_balance")
    private String factdeductbalance;

    @TableField("fact_shares")
    private String factdeductshares;

    @TableField("d_begin_date")
    private String begindate;

    @TableField("bonus_balance")
    private String bonusbalance;

    @TableField(exist = false)
    private String identitytype;

    @TableField(exist = false)
    private String identityno;

    @TableField(exist = false)
    private String tradeacco;


    private String company;

}
