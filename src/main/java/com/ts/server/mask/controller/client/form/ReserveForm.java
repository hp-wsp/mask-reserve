package com.ts.server.mask.controller.client.form;

import com.ts.server.mask.domain.Reserve;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 预定提交数据
 *
 * @author TS Group
 */
public class ReserveForm {
    @ApiModelProperty(value = "预定药铺编号", required = true)
    @NotNull
    private Integer phaId;
    @ApiModelProperty(value = "姓名", required = true)
    @NotNull
    private String name;
    @ApiModelProperty(value = "手机", required = true)
    @NotNull
    private String mobile;
    @ApiModelProperty(value = "身份证号", required = true)
    @NotNull
    private String idCard;
    @ApiModelProperty(value = "地址")
    private String address;

    public Integer getPhaId() {
        return phaId;
    }

    public void setPhaId(Integer phaId) {
        this.phaId = phaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Reserve toDomain(){
        Reserve t = new Reserve();

        t.setPhaId(phaId);
        t.setName(name);
        t.setAddress(address);
        t.setIdCard(idCard);
        t.setMobile(mobile);

        return t;
    }
}
