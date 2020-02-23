package com.ts.server.mask.controller.manage.form;

import com.ts.server.mask.domain.Pharmacy;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 新增药店提交数据
 *
 * @author TS Group
 */
public class PharmacySaveForm {
    @NotBlank
    @ApiModelProperty(value = "名称", required = true)
    private String name;
    @NotBlank
    @ApiModelProperty(value = "区域", required = true)
    private String area;
    @NotBlank
    @ApiModelProperty(value = "地址", required = true)
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Pharmacy toDomain(){
        Pharmacy t = new Pharmacy();

        t.setName(name);
        t.setAddress(address);
        t.setArea(area);

        return t;
    }
}
