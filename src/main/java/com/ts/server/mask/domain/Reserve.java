package com.ts.server.mask.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 预约订单
 *
 * @author TS Group
 */
public class Reserve {
    @ApiModelProperty("编号")
    private long id;
    @ApiModelProperty("交易编号")
    private String tarId;
    @ApiModelProperty("药铺编号")
    private int phaId;
    @ApiModelProperty("药店")
    private String pharmacy;
    @ApiModelProperty("药店地址")
    private String phaAddress;
    @ApiModelProperty("申请人姓名")
    private String name;
    @ApiModelProperty("详细地址")
    private String address;
    @ApiModelProperty("手机")
    private String mobile;
    @ApiModelProperty("身份证号")
    private String idCard;
    @ApiModelProperty("预约周期")
    private int cycle;
    @ApiModelProperty("预约个数")
    private int count;
    @ApiModelProperty("预约周期")
    private Date createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTarId() {
        return tarId;
    }

    public void setTarId(String tarId) {
        this.tarId = tarId;
    }

    public int getPhaId() {
        return phaId;
    }

    public void setPhaId(int phaId) {
        this.phaId = phaId;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    public String getPhaAddress() {
        return phaAddress;
    }

    public void setPhaAddress(String phaAddress) {
        this.phaAddress = phaAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserve reserve = (Reserve) o;
        return id == reserve.id &&
                phaId == reserve.phaId &&
                cycle == reserve.cycle &&
                count == reserve.count &&
                Objects.equals(tarId, reserve.tarId) &&
                Objects.equals(pharmacy, reserve.pharmacy) &&
                Objects.equals(phaAddress, reserve.phaAddress) &&
                Objects.equals(name, reserve.name) &&
                Objects.equals(address, reserve.address) &&
                Objects.equals(mobile, reserve.mobile) &&
                Objects.equals(idCard, reserve.idCard) &&
                Objects.equals(createTime, reserve.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tarId, phaId, pharmacy, phaAddress, name, address, mobile, idCard, cycle, count, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("tarId", tarId)
                .append("phaId", phaId)
                .append("pharmacy", pharmacy)
                .append("phaAddress", phaAddress)
                .append("name", name)
                .append("address", address)
                .append("mobile", mobile)
                .append("idCard", idCard)
                .append("cycle", cycle)
                .append("count", count)
                .append("createTime", createTime)
                .toString();
    }
}
