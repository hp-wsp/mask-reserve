package com.ts.server.mask.controller.manage.form;

import com.ts.server.mask.domain.Area;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 新增区域提交数据
 *
 * @author TS Group
 */
public class AreaSaveForm {
    @NotBlank
    @ApiModelProperty("区域名称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Area toDomain(){
        Area t = new Area();

        t.setName(name);

        return t;
    }
}
