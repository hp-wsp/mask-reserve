package com.ts.server.mask.controller.manage.form;

import com.ts.server.mask.domain.Pharmacy;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 修改店铺信息提交数据
 *
 * @author TS Group
 */
public class PharmacyUpdateForm extends PharmacySaveForm {
    @NotNull
    @ApiModelProperty(value = "编号", required = true)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Pharmacy toDomain() {
        Pharmacy t = super.toDomain();
        t.setId(id);
        return t;
    }
}
