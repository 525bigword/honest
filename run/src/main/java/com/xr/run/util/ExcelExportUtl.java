package com.xr.run.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Slf4j
public class ExcelExportUtl {
//    public static void startPage(Map<String, Object> query) {
//        int pageNum = 0;
//        int pageSize = 0;
//
//        if (query.containsKey("pageNum") && query.containsKey("pageSize")) {
//            pageNum = Integer.parseInt(String.valueOf(query.get("pageNum")));
//            pageSize = Integer.parseInt(String.valueOf(query.get("pageSize")));
//        } else {
//            log.error("StartPage Failed!");
//        }
//
//        PageHelper.startPage(pageNum, pageSize);
//    }

    public static XSSFWorkbook exportExcel(Class schema, Map<String, String> title, List<?> list) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("sheet1");

            sheet.setDefaultColumnWidth((short) 15);

            XSSFRow row = sheet.createRow(0);

            Field[] fields = schema.getDeclaredFields();

            int index1 = 0;
            for (Field field : fields) {
                if (title.containsKey(field.getName())) {
                    XSSFCell cell = row.createCell(index1);
                    XSSFRichTextString text = new XSSFRichTextString(title.get(field.getName()));
                    cell.setCellValue(text);
                    index1++;
                }
            }

            for (int i = 0, len = list.size(); i < len; i++) {
                row = sheet.createRow(i + 1);
                Object object = list.get(i);

                int index2 = 0;
                for (Field field: fields) {
                    String filedName = field.getName();

                    if (title.containsKey(filedName)) {
                        XSSFCell cell = row.createCell(index2);

                        String getMethodName = "get" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);

                        Method getMethod = schema.getMethod(getMethodName);
                        Object value = getMethod.invoke(object);
                        XSSFRichTextString text = new XSSFRichTextString(String.valueOf(value));
                        cell.setCellValue(text);

                        index2++;
                    }
                }
            }

            return workbook;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    public static void exportFile(HttpServletResponse response, XSSFWorkbook workbook) {
        if (workbook != null) {
            String fileName = "gzyr_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xlsx";
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/octet-stream;charset=GB2312");
            response.setCharacterEncoding("GB2312");
            OutputStream outputStream;
            try {
                outputStream = response.getOutputStream();
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            try {
                response.getWriter().print("error");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

}
