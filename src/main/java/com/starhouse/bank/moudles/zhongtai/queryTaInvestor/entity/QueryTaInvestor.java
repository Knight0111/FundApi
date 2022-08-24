package com.starhouse.bank.moudles.zhongtai.queryTaInvestor.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cust_info")
public class QueryTaInvestor {
    @TableField("fund_acco")
    private String fundacco;

    @TableField("cust_name")
    private String custname;

    @TableField("cust_type")
    private String custtype;

    @TableField("certi_type")
    private String identitytype;

    @TableField("certi_no")
    private String identityno;

    @TableField(exist = false)
    private String opendate;
    @TableField(exist = false)
    private String fundcode;
    @TableField(exist = false)
    private String fundname;
    @TableField(exist = false)
    private String mobileno;
    @TableField(exist = false)
    private String email;
    @TableField(exist = false)
    private String address;
    @TableField(exist = false)
    private String lastmodify;
    @TableField(exist = false)
    private String rows;


    private String company;

}
