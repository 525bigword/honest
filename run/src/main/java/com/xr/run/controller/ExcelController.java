package com.xr.run.controller;

import com.xr.run.entity.SysPermission;
import com.xr.run.util.ExcelData;
import com.xr.run.util.ExcelExportUtl;
import com.xr.run.util.ExportExcelUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExcelController {

    @RequestMapping(value = "/ExcelDownload", method = RequestMethod.GET)
    public void excel(HttpServletResponse response) throws Exception {
        ExcelData data = new ExcelData();
        data.setName("hello");
        List<String> titles = new ArrayList();
        titles.add("a1");
        titles.add("a2");
        titles.add("a3");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        List<Object> row = new ArrayList();
        row.add("11111111111");
        row.add("22222222222");
        row.add("3333333333");
        rows.add(row);

        data.setRows(rows);


        //生成本地
        /*File f = new File("c:/test.xlsx");
        FileOutputStream out = new FileOutputStream(f);
        ExportExcelUtils.exportExcel(data, out);
        out.close();*/
        ExportExcelUtils.exportExcel(response,"hello.xlsx",data);
    }

    @PostMapping("/ExcelDownload")
    private void exportExcel(HttpServletResponse response,HSSFWorkbook workbook) throws Exception {
        // 创建工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        // 工作表
        XSSFSheet sheet = wb.createSheet("学生信息表");
        // 标头行，代表第一行
        XSSFRow header = sheet.createRow(0);
        // 创建单元格，0代表第一行第一列
        XSSFCell cell = header.createCell(0);
        cell.setCellValue("学号");
        header.createCell(1).setCellValue("姓名");
        header.createCell(2).setCellValue("专业");
        header.createCell(3).setCellValue("班级");
        header.createCell(4).setCellValue("身份证号");
        header.createCell(5).setCellValue("宿舍号");
        header.createCell(6).setCellValue("报道日期");
        XSSFRow row = sheet.createRow(1);
        row.createCell(0).setCellValue("1");
        row.createCell(1).setCellValue("李城");
        row.createCell(2).setCellValue("java");
        row.createCell(3).setCellValue("java203");
        row.createCell(4).setCellValue("2345678943322");
        row.createCell(5).setCellValue("518");
        row.createCell(6).setCellValue("2100-1-29");
        // 设置列的宽度
        // getPhysicalNumberOfCells()代表这行有多少包含数据的列
        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            // POI设置列宽度时比较特殊，它的基本单位是1/255个字符大小，
            // 因此我们要想让列能够盛的下20个字符的话，就需要用255*20
            sheet.setColumnWidth(i, 255 * 20);
        }
        // 设置行高，30像素
        header.setHeightInPoints(30);
        //输出文件要么是 \\要么/否则会报错
        response.setContentType("application/vnd.ms-excel");

        response.setHeader("Content-Disposition", "Content-Disposition: attachment ; filename=123.xls");
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }
    //创建表头
    private void createTitle(HSSFWorkbook workbook,HSSFSheet sheet){
        //合并单元格
        //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
        CellRangeAddress region1 = new CellRangeAddress(0, 1, 0, 0);
        sheet.addMergedRegion(region1);
        HSSFRow row = sheet.createRow(0);
        //设置行高
        row.setHeightInPoints(18);
        //设置列宽度
        sheet.setColumnWidth(0,30*256);
        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("指标名称");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("单位");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("本月实际");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("全厂小计");
        cell.setCellStyle(style);
    }
    //生成excel文件
    public void buildExcelFile(String filename,HSSFWorkbook workbook) throws Exception{
        FileOutputStream fos = new FileOutputStream(filename);
        workbook.write(fos);
        fos.flush();
        fos.close();
    }

}
