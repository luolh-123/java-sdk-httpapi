package com.hanclouds.req;

import com.hanclouds.exception.HanCloudsClientException;
import com.hanclouds.http.AbstractDeviceKeyDataStreamPageRequest;
import com.hanclouds.http.HttpMethodEnum;
import com.hanclouds.resp.DeviceDataStreamDataPageResponse;

/**
 * 根据数据流名称获取设备数据流请求
 * @author czl
 * @version 1.0
 * @date 2018/4/24 13:46
 */
public class DeviceDataStreamDataPageRequest extends AbstractDeviceKeyDataStreamPageRequest<DeviceDataStreamDataPageResponse> {

    private Long startTime;

    private Long endTime;

    public DeviceDataStreamDataPageRequest() {
        super("/devices/{deviceKey}/datastreams/{dataName}/data");
        this.setHttpMethod(HttpMethodEnum.GET);
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {

        this.startTime = startTime;
        this.putQueryParameter("startTime", startTime.toString());
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {

        this.endTime = endTime;
        this.putQueryParameter("endTime", endTime.toString());
    }



    @Override
    public Class<DeviceDataStreamDataPageResponse> getResponseClass() {
        return DeviceDataStreamDataPageResponse.class;
    }

    @Override
    public void validate() throws HanCloudsClientException {
        super.validate();

        if (this.page <= 0) {
            throw new HanCloudsClientException("page must greater than zero");
        }
        if (this.pageSize <= 0) {
            throw new HanCloudsClientException("pageSize must greater than zero");
        }
    }
}
