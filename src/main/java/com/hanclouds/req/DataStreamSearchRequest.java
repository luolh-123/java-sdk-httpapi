package com.hanclouds.req;

import com.hanclouds.exception.HanCloudsClientException;
import com.hanclouds.http.AbstractProductKeyPageRequest;
import com.hanclouds.http.HttpMethodEnum;
import com.hanclouds.resp.DataStreamPageResponse;
import com.hanclouds.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author lih
 * @version 1.0
 * @date 2018/5/30 15:22
 * 通过dataName左匹配模糊查询
 */
public class DataStreamSearchRequest extends AbstractProductKeyPageRequest<DataStreamPageResponse> {

    protected String dataName;


    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
        this.putQueryParameter("dataName", dataName);
    }



    public DataStreamSearchRequest() {
        super("/products/{productKey}/datastreams/search");
        this.setHttpMethod(HttpMethodEnum.GET);
    }

    @Override
    public Class<DataStreamPageResponse> getResponseClass() {
        return DataStreamPageResponse.class;
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
    }

}
