package com.ts.server.mask.controller.client;

import com.ts.server.mask.configure.MaskProperties;
import com.ts.server.mask.controller.client.vo.InitClientVo;
import com.ts.server.mask.controller.vo.ResultVo;
import com.ts.server.mask.domain.Area;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 初始预定信息
 *
 * @author TS Group
 */
@RestController
@RequestMapping("/client")
@Api(value = "/client", tags = "C-初始化信息")
public class InitClientController {
    private final CacheDataService cacheService;
    private final MaskProperties properties;

    @Autowired
    public InitClientController(CacheDataService cacheService, MaskProperties properties) {
        this.cacheService = cacheService;
        this.properties = properties;
    }

    @GetMapping(value = "init", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("获取初始信息")
    public ResultVo<InitClientVo> init(){
        LocalTime time = LocalTime.now();
        int hour = time.getHour();
        int minute = time.getMinute();
        boolean pass = (hour >= properties.getFromHour() &&  minute >= properties.getFromMinute())
                && (hour <= properties.getToHour() && minute <= properties.getToMinute());

        return ResultVo.success(new InitClientVo(properties.getFromHour(), properties.getFromMinute(),
                properties.getToHour(), properties.getToMinute(), properties.getNotice(), properties.getLimit(),
                pass));
    }

    @GetMapping(value = "areas", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("获取所有区域")
    public ResultVo<List<Area>> getAreas(){
        return ResultVo.success(cacheService.getAreas());
    }

    @GetMapping(value = "pharmacy", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("获取药店")
    public ResultVo<List<CacheDataService.SimplePharmacy>> getPharmacy(@RequestParam(value = "area") String area){
        return ResultVo.success(cacheService.getPharmacies(area));
    }
}
