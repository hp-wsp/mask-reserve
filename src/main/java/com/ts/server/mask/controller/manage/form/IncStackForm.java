package com.ts.server.mask.controller.manage.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 新增库存提交数据
 *
 * @author TS Group
 */
public class IncStackForm {
    @ApiModelProperty(value = "编号", required = true)
    @NotNull
    private Integer id;
    @ApiModelProperty(value = "增加库存", required = true)
    @NotNull
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
