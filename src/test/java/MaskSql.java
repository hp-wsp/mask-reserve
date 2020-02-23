import com.ts.server.mask.domain.Pharmacy;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Stack;

public class MaskSql {
    public static void main(String[] args)throws IOException {
        InputStream stream = MaskSql.class.getResourceAsStream("dddd.xlsx");
        Workbook workbook = WorkbookFactory.create(stream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.rowIterator();
        Stack<Pharmacy> all = new Stack<>();

        int i = 0;
        for(;iterator.hasNext();){
            Row row = iterator.next();
            if(i++ > 1){
                all.push(build(i, row));
            }
        }

        printSql(all);
    }

    private static Pharmacy build(int index, Row row){
        Pharmacy t = new Pharmacy();

        String area = getCellValue(row.getCell(0));
        String name = getCellValue(row.getCell(1));
        String address = getCellValue(row.getCell(2));

        t.setArea(area);
        t.setName(name);
        t.setAddress(address);

        return t;
    }

    private static String getCellValue(Cell cell){
        String v;
        if(cell.getCellType() == CellType.NUMERIC){
            v = String.valueOf(cell.getNumericCellValue());
        }else{
            v = cell.getStringCellValue();
        }
        if(v == null){
            return "";
        }
        return StringUtils.replace(StringUtils.trim(v), "'", "''");
    }

    private static void printSql(Stack<Pharmacy> all){
        System.out.println("INSERT INTO t_pharmacy (area, name, address, stack, sell, version, update_time, create_time) VALUES ");
        for (Pharmacy t : all){
            String s = String.format("('%s','%s', '%s', 0, 0, 0, now(), now()),",
                    t.getArea(), t.getName(), t.getAddress());
            System.out.println(s);
        }
    }

}
