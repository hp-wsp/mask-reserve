package com.ts.server.mask.controller.form;

import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 修改密码提交数据
 *
 * @author TS Group
 */
@ApiModel("修改密码提交数据")
public class PasswordUpdateForm {
    @NotBlank
    private String password;
    @NotBlank
    @Size(min = 6, max = 20)
    private String newPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
