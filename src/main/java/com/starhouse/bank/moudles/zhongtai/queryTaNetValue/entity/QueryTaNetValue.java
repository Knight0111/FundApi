package com.starhouse.bank.moudles.zhongtai.queryTaNetValue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("fund_net_val")
public class QueryTaNetValue  {
    @TableField("fund_code")
     private String fundcode;

    @TableField("fund_name")
     private String fundname;

    @TableField("net_asset_val")
     private String dwjz;

    @TableField("total_asset_val")
     private String ljdwjz;

    @TableField("asset_net")
     private String zcjz;

    @TableField("asset_shares")
     private String zcfe;

    @TableField("asset_total")
     private String zchj;

    @TableField(exist = false)
    private String sfqr;

    @TableField("net_date")
    private String rq;

     private String company;
}
