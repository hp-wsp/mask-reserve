package com.ts.server.mask.controller.manage;

import com.ts.server.mask.BaseException;
import com.ts.server.mask.controller.form.PasswordUpdateForm;
import com.ts.server.mask.controller.manage.form.ManagerInfoForm;
import com.ts.server.mask.controller.vo.OkVo;
import com.ts.server.mask.controller.vo.ResultVo;
import com.ts.server.mask.domain.Manager;
import com.ts.server.mask.security.Credential;
import com.ts.server.mask.security.CredentialContextUtils;
import com.ts.server.mask.service.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 管理端基础API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/account")
@Api(value = "/man/account", tags = "管理端基础API接口")
public class AccountManController {

    private final ManagerService managerService;

    @Autowired
    public AccountManController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping(value = "updatePassword", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改申报员密码")
    public ResultVo<OkVo> updatePassword(@Validated @RequestBody PasswordUpdateForm form){
        return ResultVo.success(new OkVo(managerService.updatePassword(getCredential().getId(), form.getPassword(), form.getNewPassword())));
    }

    @PutMapping(value = "account", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改管理员信息")
    public ResultVo<Manager> updateAccount(@Validated @RequestBody ManagerInfoForm form){
        Manager m = managerService.get(getCredential().getId());
        m.setName(form.getName());
        m.setPhone(form.getPhone());

        return ResultVo.success(managerService.update(m));
    }


    private Credential getCredential(){
        return CredentialContextUtils.getCredential().orElseThrow(() -> new BaseException("用户未授权"));
    }
}
