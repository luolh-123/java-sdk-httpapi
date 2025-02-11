package com.hanclouds;

import com.hanclouds.exception.HanCloudsClientException;
import com.hanclouds.exception.HanCloudsException;
import com.hanclouds.exception.HanCloudsServerException;
import com.hanclouds.http.BaseHttpResponse;
import com.hanclouds.http.AbstractHttpRequest;
import com.hanclouds.http.AbstractHttpResponse;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * @author czl
 * @version 1.0
 * @date 2018/4/8 16:20
 */
public class HanCloudsClient {

    private String gatewayUrl;

    /**
     * 实体Key
     * 多个key共存情况下按以下优先顺序读取，不会在客户端检测是否有相关权限
     * userKey, productKey, deviceKey
     */
    private String userKey;
    private String productKey;
    private String deviceKey;

    private String userAuthKey;
    private String productServiceKey;

    /**
     * Device 对应 token
     */
    private String secretKey;

    /**
     * @param gatewayUrl 参数必须为平台网关路径前缀，例如: http://api.hanclouds.com/api/v1
     */
    public HanCloudsClient(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public void putUserAuthParams(String userKey, String authKey, String secretKey) {
        if (userKey == null || userKey.isEmpty()
                || authKey == null || authKey.isEmpty()
                || secretKey == null || secretKey.isEmpty()) {
            return;
        }

        this.userKey = userKey;
        this.userAuthKey = authKey;
        this.secretKey = secretKey;
    }

    public void putProductAuthParams(String productKey, String serviceKey, String secretKey) {
        if (productKey == null || productKey.isEmpty()
                || serviceKey == null || serviceKey.isEmpty()
                || secretKey == null || secretKey.isEmpty()) {
            return;
        }

        this.productKey = productKey;
        this.productServiceKey = serviceKey;
        this.secretKey = secretKey;
    }

    public void putDeviceAuthParams(String deviceKey, String deviceToken) {
        if (deviceKey == null || deviceKey.isEmpty() || deviceToken == null || deviceToken.isEmpty()) {
            return;
        }

        this.deviceKey = deviceKey;
        this.secretKey = deviceToken;
    }

    /**
     * 执行对应request并返回指定Response
     * @param request
     * @param <T>
     * @return
     */
    public <T extends AbstractHttpResponse> T execute(AbstractHttpRequest<T> request) throws HanCloudsException {
        if (request == null) {
            throw new HanCloudsClientException("request is null");
        }


        if (this.secretKey == null || this.secretKey.isEmpty()) {
            throw new HanCloudsClientException("no secretKey for request");
        }

        boolean hadKey = false;
        //放入 key 参数
        if (this.userKey != null && !this.userKey.isEmpty() && this.userAuthKey != null && !this.userAuthKey.isEmpty()) {
            request.putHeader("HC-USER-KEY", this.userKey);
            request.putHeader("HC-USER-AUTH-KEY", this.userAuthKey);
            hadKey = true;
        }

        if (!hadKey && this.productKey != null && !this.productKey.isEmpty()
                && this.productServiceKey != null && !this.productServiceKey.isEmpty()) {
            request.putHeader("HC-PRODUCT-KEY", this.productKey);
            request.putHeader("HC-PRODUCT-SERVICE-KEY", this.productServiceKey);
            hadKey = true;
        }

        if (!hadKey && this.deviceKey != null && !this.deviceKey.isEmpty()) {
            request.putHeader("HC-DEVICE-KEY", this.deviceKey);
            hadKey = true;
        }

        if (!hadKey) {
            throw new HanCloudsClientException("no auth keys for request");
        }

        request.validate();

        //request签名生成必要参数
        request.signAndPutQueryParams(this.secretKey);

        HttpURLConnection httpURLConnection = request.getHttpConnection(this.gatewayUrl);
        if (httpURLConnection == null) {
            throw new HanCloudsClientException("request get http connection error");
        }

        try {
            httpURLConnection.connect();
        } catch (IOException e) {
            throw new HanCloudsServerException(e.getMessage());
        }

        //获取response
        BaseHttpResponse baseHttpResponse = null;
        try {
            baseHttpResponse = BaseHttpResponse.getResponse(httpURLConnection);
        } catch (IOException e) {
            throw new HanCloudsServerException(e.getMessage());
        } finally {
            httpURLConnection.disconnect();
        }

        if (baseHttpResponse == null) {
            throw new HanCloudsException("system error");
        }

        T resp = null;
        try {
            resp = request.getResponseClass().newInstance();
        } catch (Exception e) {
            throw new HanCloudsException(e.getMessage());
        }

        resp.setBaseHttpResponse(baseHttpResponse);
        resp.parseBaseHttpResponse();

        return resp;
    }
}
