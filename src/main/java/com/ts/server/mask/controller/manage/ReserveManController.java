package com.ts.server.mask.controller.manage;

import com.ts.server.mask.BaseException;
import com.ts.server.mask.common.excel.ExcelWriter;
import com.ts.server.mask.common.excel.HttpExcelWriter;
import com.ts.server.mask.controller.manage.excel.ReserveExcelWriter;
import com.ts.server.mask.controller.vo.ResultPageVo;
import com.ts.server.mask.domain.Pharmacy;
import com.ts.server.mask.domain.Reserve;
import com.ts.server.mask.service.PharmacyService;
import com.ts.server.mask.service.ReserveService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 预约管理
 *
 * @author TS Group
 */
@RestController
@RequestMapping("/man/reserve")
public class ReserveManController {
    private final ReserveService service;
    private final PharmacyService pharmacyService;

    @Autowired
    public ReserveManController(ReserveService service, PharmacyService pharmacyService) {
        this.service = service;
        this.pharmacyService = pharmacyService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询药店")
    public ResultPageVo<Reserve> query(
            @ApiParam(value = "预约人姓名") @RequestParam(required = false) String name,
            @ApiParam(value = "药店") @RequestParam(required = false) String pharmacy,
            @ApiParam(value = "开始时间") @RequestParam(defaultValue = "2020-01-01") String fromDate,
            @ApiParam(value = "开始时间") @RequestParam(defaultValue = "2021-01-01") String toDate,
            @RequestParam(defaultValue = "true")@ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        Date fromTime = toDate(fromDate, 0);
        Date toTime = toDate(toDate, 0);
        return new ResultPageVo.Builder<>(page, rows, service.query(name, pharmacy, fromTime, toTime, page * rows, rows))
                .count(isCount, () -> service.count(name, pharmacy, fromTime, toTime))
                .build();
    }

    private Date toDate(String dateStr, int day){
        try{
            LocalDate localDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            localDate = localDate.plusDays(day);
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            return Date.from(instant);
        }catch (Exception e){
            throw new BaseException("日期格式错误");
        }
    }

    @GetMapping(value = "export")
    public void exportExcel(@ApiParam("店铺编号")@RequestParam Integer phaId,
                            @ApiParam("日期") @RequestParam String date,
                            HttpServletResponse response){

        Pharmacy pharmacy = pharmacyService.get(phaId);

        Date fromTime = toDate(date, 0);
        Date toTime = toDate(date, 1);

        final String filename = String.format("%s(%s)", pharmacy.getName(), date);

        try(ExcelWriter<Reserve> writer = new ReserveExcelWriter(response, false, filename);){
            final int row = 500;
            for(int i = 0; i < 100; i++){
                int offset = row * i;
                List<Reserve> data = service.query("", pharmacy.getName(), fromTime, toTime, offset, row);
                if(data.isEmpty()){
                    break;
                }
                writer.write(offset, data);
            }
        }catch (IOException e){
            throw new BaseException("导出预约信息错误");
        }
    }
}
