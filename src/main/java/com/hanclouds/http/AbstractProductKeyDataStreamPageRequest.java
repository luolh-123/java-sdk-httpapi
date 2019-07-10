package com.hanclouds.http;

import com.hanclouds.exception.HanCloudsClientException;
import com.hanclouds.http.AbstractHttpResponse;
import com.hanclouds.http.AbstractProductKeyPageRequest;
import com.hanclouds.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author czl
 * @version 1.0
 * @date 2018/7/13 17:24
 */
public abstract class AbstractProductKeyDataStreamPageRequest<T extends AbstractHttpResponse> extends AbstractProductKeyPageRequest<T> {
    protected String dataName;

    public AbstractProductKeyDataStreamPageRequest(String url) {
        super(url);
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    @Override
    public void validate() throws HanCloudsClientException {
        super.validate();

        if (StringUtils.isBlank(this.dataName)) {
            throw new HanCloudsClientException("dataName can not null or empty");
        }

        try {
            dataName = URLEncoder.encode(dataName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }

        this.setUrl(this.getUrl().replace("{dataName}", this.dataName));
    }
}