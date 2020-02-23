package com.ts.server.mask.controller.manage;

import com.ts.server.mask.BaseException;
import com.ts.server.mask.controller.manage.form.LoginForm;
import com.ts.server.mask.controller.manage.vo.LoginVo;
import com.ts.server.mask.controller.vo.OkVo;
import com.ts.server.mask.controller.vo.ResultVo;
import com.ts.server.mask.domain.Manager;
import com.ts.server.mask.security.Credential;
import com.ts.server.mask.security.token.TokenService;
import com.ts.server.mask.service.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 管理端通用API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/main")
@Api(value = "/man/main", tags = "管理端通用API接口")
public class MainManController {

    private final ManagerService managerService;
    private final TokenService tokenService;

    @Autowired
    public MainManController(ManagerService managerService, TokenService tokenService) {

        this.managerService = managerService;
        this.tokenService = tokenService;
    }

    @PostMapping(value = "login", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("管理员登录")
    public ResultVo<LoginVo<Manager>> login(@Valid @RequestBody LoginForm form, HttpServletRequest request){

        Optional<Manager> optional = managerService.getValidate(form.getUsername(), form.getPassword());
        if(!optional.isPresent()){
            throw new BaseException(560, "用户名或密码错误");
        }

        Manager m = optional.get();
        Credential credential = new Credential(m.getId(), m.getUsername(),
                Arrays.asList(m.getRole(), "ROLE_AUTHENTICATION"));
        String token = tokenService.generate(credential);

        return ResultVo.success(new LoginVo<>(token, m));
    }

    @GetMapping(value = "logout", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("管理员退出")
    public ResultVo<OkVo> logout(@RequestHeader(value = "token", required = false)String token,
                                 @RequestHeader(value = "Authorization", required = false) String auth){

        exit(token, auth);
        return ResultVo.success(new OkVo(true));
    }

    private void exit(String token, String auth){
        if(StringUtils.isBlank(token) && StringUtils.isBlank(auth)){
            return ;
        }

        if(StringUtils.isNotBlank(token)){
            tokenService.destroy(token);
            return ;
        }

        String t = StringUtils.trim(StringUtils.removeStart(auth, "Bearer"));
        tokenService.destroy(t);
    }
}
