package com.ts.server.mask.controller.manage;

import com.ts.server.mask.controller.manage.form.AreaSaveForm;
import com.ts.server.mask.controller.manage.form.AreaUpdateForm;
import com.ts.server.mask.controller.vo.OkVo;
import com.ts.server.mask.controller.vo.ResultPageVo;
import com.ts.server.mask.controller.vo.ResultVo;
import com.ts.server.mask.domain.Area;
import com.ts.server.mask.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 区域管理API
 *
 * @author TS Group
 */
@RestController
@RequestMapping("/man/area")
@Api(value = "/man/area", tags = "M-区域管理")
public class AreaManController {
    private final AreaService service;

    @Autowired
    public AreaManController(AreaService service) {
        this.service = service;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("新增区域")
    public ResultVo<Area> save(@Valid @RequestBody AreaSaveForm form){
        return ResultVo.success(service.save(form.toDomain()));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("修改区域")
    public ResultVo<Area> update(@Valid @RequestBody AreaUpdateForm form){
        return ResultVo.success(service.update(form.toDomain()));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("获取区域")
    public ResultVo<Area> get(@PathVariable("id")int id){
        return ResultVo.success(service.get(id));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("删除区域")
    public ResultVo<OkVo> delete(@PathVariable("id")int id){
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询区域")
    public ResultPageVo<Area> query(
            @ApiParam(value = "名称") @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return new ResultPageVo.Builder<>(page, rows, service.query(name, page * rows, rows))
                .count(isCount, () -> service.count(name))
                .build();
    }
}
