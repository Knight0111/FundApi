package com.starhouse.bank.moudles.zhongtai.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("share_holder_info")
public class QueryInvestorShare {

    @TableField("fund_code")
    private String fundcode;
    @TableField("fund_name")
    private String fundname;
    @TableField("fund_acco")
    private String fundacco;
    @TableField("cust_name")
    private String custname;
    @TableField("cust_type")
    private String custtype;

    @TableField(exist = false)
    private String identitytype;
    @TableField(exist = false)
    private String identityno;

    @TableField("real_shares")
    private String realshares;
    @TableField("net_value")
    private String dwjz;

    @TableField("net_date")
    private String rq;

    private String company;

}
