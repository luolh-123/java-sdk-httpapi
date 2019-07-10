package com.hanclouds.req;

import com.hanclouds.http.AbstractProductKeyRequest;
import com.hanclouds.http.HttpMethodEnum;
import com.hanclouds.resp.StringResponse;

/**
 * 获取数据secret
 * @author czl
 * @version 1.0
 * @date 2018/9/17 9:52
 */
public class CloudsAccessSecretRequest extends AbstractProductKeyRequest<StringResponse> {

    public CloudsAccessSecretRequest() {
        super("/clouds/{productKey}/secret");
        this.setHttpMethod(HttpMethodEnum.GET);
    }

    @Override
    public Class<StringResponse> getResponseClass() {
        return StringResponse.class;
    }
}
