package com.starhouse.bank.guotai.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cust_info")
public class CustInfo {
    private String custName;
    private String fundAcco;
    private String tradeAcco;

    @TableField(exist = false)
    private String agencyNo;

    private String agencyName;
    private String custType;

    @TableField(exist = false)
    private String custTypeName;

    private String certiType;

    @TableField(exist = false)
    private String certiTypeName;

    private String certiNo;

    @TableField(exist = false)
    private String bankNo;

    private String bankName;
    private String bankAcco;
    private String openBankName;
    private String nameInBank;

    @TableField(exist = false)
    private String createTime;
    private String company;

}
