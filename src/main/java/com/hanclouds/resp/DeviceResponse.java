package com.hanclouds.resp;

import com.alibaba.fastjson.JSON;
import com.hanclouds.http.AbstractHttpResponse;
import com.hanclouds.http.BaseHttpResponse;
import com.hanclouds.model.DeviceDTO;

/**
 * @author czl
 * @version 1.0
 * @date 2018/9/19 13:11
 */
public class DeviceResponse extends AbstractHttpResponse {

    private DeviceDTO response;

    public DeviceDTO getResponse() {
        return response;
    }

    public void setResponse(DeviceDTO response) {
        this.response = response;
    }


    @Override
    public void parseBaseHttpResponse() {
        BaseHttpResponse baseHttpResponse = this.baseHttpResponse;
        if (baseHttpResponse == null || baseHttpResponse.getBodyContent() == null) {
            return;
        }

        response = JSON.parseObject(baseHttpResponse.getBodyContent(), DeviceDTO.class);
    }

}
