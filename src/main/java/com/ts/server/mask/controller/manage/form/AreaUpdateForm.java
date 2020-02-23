package com.ts.server.mask.controller.manage.form;

import com.ts.server.mask.domain.Area;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 修改区域
 *
 * @author TS Group
 */
public class AreaUpdateForm extends AreaSaveForm {
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
    public Area toDomain() {
        Area t = super.toDomain();
        t.setId(id);
        return t;
    }
}
