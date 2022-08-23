package com.starhouse.bank.zhongtai.queryTaFundShare.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("share_change_info")
public class QueryTaFundShare {
    private String fundacco;
    private String custname;
    private String agencyname;
    private String fundcode;
    private String fundname;
    private String lastmodify;
    private String realshares;
    private String custtype;
    private String identitytype;
    private String identityno;
    private String tradeacco;
    private String frozenshares;
    private String bonustype;
}
