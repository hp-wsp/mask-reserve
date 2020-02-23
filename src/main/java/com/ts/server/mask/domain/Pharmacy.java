package com.ts.server.mask.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 店铺
 *
 * @author TS Group
 */
public class Pharmacy {
    @ApiModelProperty("编号")
    private int id;
    @ApiModelProperty("区域")
    private String area;
    @ApiModelProperty("店名")
    private String name;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("库存")
    private int stack;
    @ApiModelProperty("预定量")
    private int sell;
    @ApiModelProperty("版本号")
    private int version;
    @ApiModelProperty("修改时间")
    private Date updateTime;
    @ApiModelProperty("创建时间")
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public int getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }

    public int getSell() {
        return sell;
    }

    public void setSell(int sell) {
        this.sell = sell;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        Pharmacy pharmacy = (Pharmacy) o;
        return id == pharmacy.id &&
                stack == pharmacy.stack &&
                sell == pharmacy.sell &&
                version == pharmacy.version &&
                Objects.equals(area, pharmacy.area) &&
                Objects.equals(name, pharmacy.name) &&
                Objects.equals(address, pharmacy.address) &&
                Objects.equals(updateTime, pharmacy.updateTime) &&
                Objects.equals(createTime, pharmacy.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, area, name, address, stack, sell, version, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("area", area)
                .append("name", name)
                .append("address", address)
                .append("stack", stack)
                .append("sell", sell)
                .append("version", version)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
