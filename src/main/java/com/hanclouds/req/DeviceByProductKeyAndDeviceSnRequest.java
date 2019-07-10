package com.hanclouds.req;

import com.hanclouds.exception.HanCloudsClientException;
import com.hanclouds.http.AbstractProductKeyRequest;
import com.hanclouds.http.HttpMethodEnum;
import com.hanclouds.resp.DeviceResponse;
import com.hanclouds.util.StringUtils;

/**
 * 根据产品key和sn获取设备信息
 * @author czl
 * @version 1.0
 * @date 2018/9/19 13:21
 */
public class DeviceByProductKeyAndDeviceSnRequest extends AbstractProductKeyRequest<DeviceResponse> {

    private String deviceSn;

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
        this.putQueryParameter("deviceSn", deviceSn);
    }


    public DeviceByProductKeyAndDeviceSnRequest() {
        super("/products/{productKey}/deviceBySn");
        this.setHttpMethod(HttpMethodEnum.GET);
    }

    @Override
    public Class<DeviceResponse> getResponseClass() {
        return DeviceResponse.class;
    }

    @Override
    public void validate() throws HanCloudsClientException {
        super.validate();
        if (StringUtils.isBlank(deviceSn)) {
            throw new HanCloudsClientException("deviceSn can not null or empty");
        }
    }

}
