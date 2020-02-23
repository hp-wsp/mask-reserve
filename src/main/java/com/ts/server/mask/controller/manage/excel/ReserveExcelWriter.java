package com.ts.server.mask.controller.manage.excel;

import com.ts.server.mask.common.excel.HttpExcelWriter;
import com.ts.server.mask.domain.Reserve;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 写预约到Excel报表中
 *
 * @author TS Group
 */
public class ReserveExcelWriter extends HttpExcelWriter<Reserve> {
    private static final String[] HEADS = new String[]{"预约号", "姓名", "身份证号", "手机号", "预约数量"};

    public ReserveExcelWriter(HttpServletResponse response, boolean is2003, String filename) throws IOException {
        super(response, is2003, filename);
    }

    @Override
    protected void writeRow(Row row, Reserve reserve) {
        Cell cell0 = row.createCell(0);
        cell0.setCellValue(reserve.getTarId());
        Cell cell1 = row.createCell(1);
        cell1.setCellValue(reserve.getName());
        Cell cell2 = row.createCell(2);
        cell2.setCellValue(fuzzyIdCard(reserve.getIdCard()));
        Cell cell3 = row.createCell(3);
        cell3.setCellValue(reserve.getMobile());
        Cell cell4 = row.createCell(4);
        cell4.setCellValue(reserve.getCount());
    }

    private String fuzzyIdCard(String idCard){
        String start = StringUtils.left(idCard, 4);
        String end = StringUtils.right(idCard, 4);
        return start + "**********" + end;
    }

    @Override
    protected int writeHeader(Workbook workbook, Sheet sheet) {
        Row row = sheet.createRow(0);
        for(int i = 0; i < HEADS.length; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(HEADS[i]);
        }
        return 1;
    }
}
