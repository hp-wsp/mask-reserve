package com.ts.server.mask.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 口罩预约配置文件
 *
 * @author TS Group
 */
@ConfigurationProperties(prefix = "mask")
public class MaskProperties {

    /**
     * 预约开始小时
     */
    private int fromHour = 9;

    /**
     * 预约开始分
     */
    private int fromMinute = 0;

    /**
     * 预约结束小时
     */
    private int toHour = 21;

    /**
     * 预约结束分
     */
    private int toMinute = 0;

    /**
     * 公告
     */
    private String notice = "我县自2月24日起，启动网上预约购买口罩，首批7万个，投放到83个药店（含乡镇），每预约成功一人，则药店库存数量减少5个，当库存数量为零时则不能再预约，以后根据实际情况进行投放，广大居民可持续关注，不要抢购，不要囤积。修水县居民凭身份证和手机号码，根据就近原则进行网上预约，预约时间每天上午9时到晚上9时。预约成功者须领取“修易通”电子通行证，凭预约成功截图和本人身份证县城范围内第二天、乡镇三天后上午8时至下午6时到预约的药店购买口罩，每个1元。每个预约成功的身份证一次限购5个口罩，每个身份证每两天可预约一次。";

    /**
     * 预定个数
     */
    private int limit = 5;

    /**
     * 领取间隔天数
     */
    private int intDay = 2;

    /**
     * 交易编号开始位置
     */
    private int traIdOffset = 0;

    /**
     * 交易编号跳过
     */
    private int traIdSkip = 1;

    public int getFromHour() {
        return fromHour;
    }

    public void setFromHour(int fromHour) {
        this.fromHour = fromHour;
    }

    public int getFromMinute() {
        return fromMinute;
    }

    public void setFromMinute(int fromMinute) {
        this.fromMinute = fromMinute;
    }

    public int getToHour() {
        return toHour;
    }

    public void setToHour(int toHour) {
        this.toHour = toHour;
    }

    public int getToMinute() {
        return toMinute;
    }

    public void setToMinute(int toMinute) {
        this.toMinute = toMinute;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getIntDay() {
        return intDay;
    }

    public void setIntDay(int intDay) {
        this.intDay = intDay;
    }

    public int getTraIdOffset() {
        return traIdOffset;
    }

    public void setTraIdOffset(int traIdOffset) {
        this.traIdOffset = traIdOffset;
    }

    public int getTraIdSkip() {
        return traIdSkip;
    }

    public void setTraIdSkip(int traIdSkip) {
        this.traIdSkip = traIdSkip;
    }
}
