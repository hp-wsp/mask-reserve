package com.ts.server.mask.controller.manage.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 登陆提交数据
 *
 * @author TS Group
 */
@ApiModel("登陆提交数据")
public class LoginForm {
    @NotBlank
    @ApiModelProperty(value = "登陆用户名", required = true)
    private String username;
    @NotBlank
    @ApiModelProperty(value = "登陆密码", required = true)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
