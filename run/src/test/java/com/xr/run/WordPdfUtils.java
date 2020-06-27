package com.xr.run;

import com.aspose.words.Document;
import com.aspose.words.License;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class WordPdfUtils {
    public static void main(String[] args) {
        doc2pdf("C:/File_rec/tmp/2020/06/19/13/40/50/f537b4c4-a19a-4313-a919-674c07a92ada.docx","C:/Users/Administrator/Downloads/de5666a4-d99e-4fbf-bb5c-8011e72fa3c8.pdf");
    }
    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = WordPdfUtils.class.getClassLoader().getResourceAsStream("license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void doc2pdf(String inPath, String outPath) {
        if (!getLicense()) {// 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        FileOutputStream os =null;
        try {
            long old = System.currentTimeMillis();
            File file = new File(outPath);  //新建一个pdf文档
            os = new FileOutputStream(file);
            Document doc = new Document(inPath);                    //Address是将要被转化的word文档
            doc.save(os, com.aspose.words.SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            os.close();
            System.out.println("word turn to pdf token：" + ((now - old) / 1000.0) + "s");  //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Test
    public void gr(){
        String a="gfg/hgh/hjgh.docx";
        String b="gfg/hgh/hjgh.pdf";
        String c="gfg/hgh/hjgh.doc";
        if(a.contains(".doc")||a.contains(".docx")){
            System.out.println("a");
        }
        String substring = a.substring(a.lastIndexOf("."), a.length() - 1);
        String substring1 = a.substring(0, a.lastIndexOf("."));
        System.out.println(substring1);
    }
}
