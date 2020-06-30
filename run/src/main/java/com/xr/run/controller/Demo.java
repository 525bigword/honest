package com.xr.run.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SysMechanism;
import com.xr.run.entity.SysPost;
import com.xr.run.entity.Wind;
import com.xr.run.service.SysMechanismService;
import com.xr.run.service.SysPostService;
import com.xr.run.service.WindService;
import com.xr.run.util.DateUtil;
import com.xr.run.util.ExcelExportUtl;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/export")
public class Demo {
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private SysMechanismService sysMechanismService;
    @Autowired
    private SysPostService sysPostService;
    @Autowired
    private WindService windService;
    @RequestMapping("/mechanism")
    public void exportData() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("部门表");
        List<SysMechanism> allSysMechanism = sysMechanismService.findAllSysMechanism();

        String fileName =  DateUtil.getDate() +"部门信息.xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据

        int rowNum = 1;

        String[] headers = { "序号","ID", "部门名", "负责人", "父级部门", "分管领导", "创建时间", "创建人", "状态"};
        //headers表示excel表中第一行的表头

        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头

        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //在表中存放查询到的数据放入对应的列
        for (SysMechanism sysMechanism : allSysMechanism) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(rowNum);
            row1.createCell(1).setCellValue(sysMechanism.getMid());
            row1.createCell(2).setCellValue(sysMechanism.getMechanismName());
            row1.createCell(3).setCellValue(sysMechanism.getSname());
            row1.createCell(4).setCellValue(sysMechanism.getParentName());
            row1.createCell(5).setCellValue(sysMechanism.getBranchName());
            row1.createCell(6).setCellValue(sysMechanism.getCreateTime());
            row1.createCell(7).setCellValue(sysMechanism.getCreateName());
            row1.createCell(8).setCellValue(sysMechanism.getStaus());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/post")
    public void exportpost() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("岗位表");
        List<SysPost> allSysPost = sysPostService.getAllSysPost();

        String fileName =  DateUtil.upDate(DateUtil.getDate()) +"岗位信息.xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据

        int rowNum = 1;

        String[] headers = { "序号","ID", "岗位名", "所属部门", "描述", "创建时间", "创建人", "状态"};
        //headers表示excel表中第一行的表头

        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头

        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //在表中存放查询到的数据放入对应的列
        for (SysPost sysPost : allSysPost) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(rowNum);
            row1.createCell(1).setCellValue(sysPost.getPid());
            row1.createCell(2).setCellValue(sysPost.getPname());
            row1.createCell(3).setCellValue(sysPost.getMname());
            row1.createCell(4).setCellValue(sysPost.getMessage());
            row1.createCell(6).setCellValue(sysPost.getCreateTime());
            row1.createCell(7).setCellValue(sysPost.getCreatename());
            row1.createCell(8).setCellValue(sysPost.getStaus().equals("1")?"正常":"删除");
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
