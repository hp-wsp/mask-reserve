package com.ts.server.mask.controller.client;

import com.ts.server.mask.BaseException;
import com.ts.server.mask.common.utils.IDCardUtils;
import com.ts.server.mask.configure.MaskProperties;
import com.ts.server.mask.controller.client.form.ReserveForm;
import com.ts.server.mask.controller.vo.HasVo;
import com.ts.server.mask.controller.vo.ResultVo;
import com.ts.server.mask.domain.Reserve;
import com.ts.server.mask.service.ReserveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalTime;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 预定API
 *
 * @author TS Group
 */
@RestController
@RequestMapping("/client/reserve")
@Api(value = "/client/reserve", tags = "C-预定API")
public class ReserveClientController {

    private final ReserveService service;
    private final MaskProperties properties;

    @Autowired
    public ReserveClientController(ReserveService service, MaskProperties properties) {
        this.service = service;
        this.properties = properties;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("预定")
    public ResultVo<Reserve> reserve(@Valid @RequestBody ReserveForm form){
        if(!isTimeEnabled()){
            throw new BaseException(416, "不在预约时间");
        }
        if(!IDCardUtils.validate(form.getIdCard())){
            throw new BaseException(415, "身份证号不正确");
        }
        return ResultVo.success(service.reserve(form.toDomain()));
    }

    private boolean isTimeEnabled(){
        LocalTime time = LocalTime.now();
        int hour = time.getHour();
        int minute = time.getMinute();
        return (hour >= properties.getFromHour() &&  minute >= properties.getFromMinute())
                && (hour <= properties.getToHour() && minute <= properties.getToMinute());
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到预定信息")
    public ResultVo<HasVo> get(@RequestParam String idCard){
        HasVo vo = service.get(idCard)
                .map(e -> HasVo.hasData(true, e))
                .orElse(HasVo.noneData(false));

        return ResultVo.success(vo);
    }
}
