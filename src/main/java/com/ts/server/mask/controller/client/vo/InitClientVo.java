package com.ts.server.mask.controller.client.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 初始化输出数据
 *
 * @author TS Group
 */
public class InitClientVo {
    @ApiModelProperty("预约开始时间（小时）")
    private final int fromHour;
    @ApiModelProperty("预约开始时间（分）")
    private final int fromMinute;
    @ApiModelProperty("预约结束时间（小时）")
    private final int toHour;
    @ApiModelProperty("预约开始时间（分）")
    private final int toMinute;
    @ApiModelProperty("公告")
    private final String notice;
    @ApiModelProperty("限制购买数量")
    private final int limit;
    @ApiModelProperty("是否可以预约")
    private final boolean pass;

    public InitClientVo(int fromHour, int fromMinute, int toHour, int toMinute,
                        String notice, int limit, boolean pass) {

        this.fromHour = fromHour;
        this.fromMinute = fromMinute;
        this.toHour = toHour;
        this.toMinute = toMinute;
        this.notice = notice;
        this.limit = limit;
        this.pass = pass;
    }

    public int getFromHour() {
        return fromHour;
    }

    public int getFromMinute() {
        return fromMinute;
    }

    public int getToHour() {
        return toHour;
    }

    public int getToMinute() {
        return toMinute;
    }

    public String getNotice() {
        return notice;
    }

    public int getLimit() {
        return limit;
    }

    public boolean isPass() {
        return pass;
    }
}
