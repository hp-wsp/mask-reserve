package com.ts.server.mask.controller.manage;

import com.ts.server.mask.controller.manage.form.IncStackForm;
import com.ts.server.mask.controller.manage.form.PharmacySaveForm;
import com.ts.server.mask.controller.manage.form.PharmacyUpdateForm;
import com.ts.server.mask.controller.vo.OkVo;
import com.ts.server.mask.controller.vo.ResultPageVo;
import com.ts.server.mask.controller.vo.ResultVo;
import com.ts.server.mask.domain.Pharmacy;
import com.ts.server.mask.service.PharmacyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 药店管理API
 *
 * @author TS Group
 */
@RestController
@RequestMapping("/man/pharmacy")
@Api(value = "/man/pharmacy", tags = "M-药店管理API")
public class PharmacyManController {
    private final PharmacyService service;

    @Autowired
    public PharmacyManController(PharmacyService service) {
        this.service = service;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("新增药店")
    public ResultVo<Pharmacy> save(@Valid @RequestBody PharmacySaveForm form){
        return ResultVo.success(service.save(form.toDomain()));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("修改药店")
    public ResultVo<Pharmacy> update(@Valid @RequestBody PharmacyUpdateForm form){
        return ResultVo.success(service.update(form.toDomain()));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到药店")
    public ResultVo<Pharmacy> get(@PathVariable("id")int id){
        return ResultVo.success(service.get(id));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("删除药店")
    public ResultVo<OkVo> delete(@PathVariable("id")int id){
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @PutMapping(value = "incStack", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("新增库存")
    public ResultVo<Pharmacy> incStack(@Valid @RequestBody IncStackForm form){
        service.incStack(form.getId(), form.getCount());
        return ResultVo.success(service.get(form.getId()));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询药店")
    public ResultPageVo<Pharmacy> query(
            @ApiParam(value = "区域") @RequestParam(required = false) String area,
            @ApiParam(value = "名称") @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return new ResultPageVo.Builder<>(page, rows, service.query(area, name, page * rows, rows))
                .count(isCount, () -> service.count(area, name))
                .build();
    }
}
