package com.wxpay;

import java.io.Serializable;

/**
 * Created by Roye on 2015/6/9.
 */
public class PlatformData implements Serializable {
    public String appId;
    public String partnerId;
    public String prepayId;
    public String timeStamp;
    public String signType;
    public String packageValue;
    public String nonceStr;
    public String paySign;
}
