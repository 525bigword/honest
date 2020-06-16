package com.xr.run;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

public class PoiTest {
    @Test
    public void Poi1Test() throws IOException {
        // 创建工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        // 工作表
        XSSFSheet sheet = wb.createSheet("表");
        // 标头行，代表第一行
        XSSFRow header = sheet.createRow(0);
        // 创建单元格，0代表第一行第一列
        XSSFCell cell = header.createCell(0);
        cell.setCellValue("1");
        header.createCell(1).setCellValue("1");
        header.createCell(2).setCellValue("1");
        header.createCell(3).setCellValue("1");
        header.createCell(4).setCellValue("1");
        header.createCell(5).setCellValue("1");
        header.createCell(6).setCellValue("1");
        XSSFRow row = sheet.createRow(1);
        row.createCell(0).setCellValue("1");
        row.createCell(1).setCellValue("123");
        row.createCell(2).setCellValue("123");
        row.createCell(3).setCellValue("123");
        row.createCell(4).setCellValue("123");
        row.createCell(5).setCellValue("23");
        row.createCell(6).setCellValue("2100-1-29");
        // 设置列的宽度
        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            sheet.setColumnWidth(i, 255 * 20);
        }
        // 设置行高，30像素
        header.setHeightInPoints(30);
        FileOutputStream fos = new FileOutputStream("C:\\Users\\83771\\Desktop\\Demo.xlsx");
        // 向指定文件写入内容
        wb.write(fos);
        //关闭流
        fos.close();
    }

}
