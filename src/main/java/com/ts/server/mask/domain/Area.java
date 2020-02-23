package com.ts.server.mask.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 区域
 *
 * @author TS Group
 */
public class Area {
    @ApiModelProperty("编号")
    private int id;
    @ApiModelProperty("区域名称")
    private String name;
    @ApiModelProperty("创建时间")
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        Area area = (Area) o;
        return id == area.id &&
                Objects.equals(name, area.name) &&
                Objects.equals(createTime, area.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("createTime", createTime)
                .toString();
    }
}
