package com.dhm.html;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * 将Excel转化为HTML
 *
 */

public class ExcelToHtml {

//    public void convertExceltoHtmlAll(String excelPath, String htmlPath) {
//        HSSFWorkbook workBook = null;
//        InputStream is = null;
//        try {
//            is = new FileInputStream(excelPath);
//            //判断Excel文件是2003版还是2007版
//            String suffix = (excelPath.substring(excelPath.lastIndexOf("."))).toLowerCase();
//            if (suffix.equals(".xlsx")) {
//                //将07版转化为03版
//                Xssf2Hssf xlsx2xls = new Xssf2Hssf();
//                XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(is);
//                workBook = new HSSFWorkbook();
//                xlsx2xls.transformXSSF(xSSFWorkbook, workBook);
//            } else {
//                workBook = new HSSFWorkbook(is);
//            }
//            convertWorkBook(workBook, htmlPath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 根据sheet来生成html
     *
     * @param excelPath
     * @param htmlDir
     * @param fileNameWithout
     */
    public void convertExceltoHtml(String excelPath, String htmlDir, String fileNameWithout) {
        List<HSSFWorkbook> list = Lists.newArrayList();
        HSSFWorkbook workBook = null;
        InputStream is = null;
        try {
            is = new FileInputStream(excelPath);
            //判断Excel文件是2003版还是2007版
            String suffix = (excelPath.substring(excelPath.lastIndexOf("."))).toLowerCase();
            int number;
            if (suffix.equals(".xlsx")) {
                //将07版转化为03版
                Xssf2Hssf xlsx2xls = new Xssf2Hssf();
                XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(is);
                number = xSSFWorkbook.getNumberOfSheets();
                for (int i = 0; i < number; i++) {
                    workBook = new HSSFWorkbook();
                    xlsx2xls.transformXSSF(xSSFWorkbook, workBook);
                    list.add(workBook);
                }
            } else {
                System.out.println("ssssss");
                workBook = new HSSFWorkbook(is);
                number = workBook.getNumberOfSheets();
                for (int i = 0; i < number; i++) {
                    list.add(workBook);
                }
            }
            for (int i = 0; i < number; i++) {
                HSSFWorkbook book = list.get(i);
                for (int j = 0; j < number; j++) {
                    if (j != i) {
                        book.removeSheetAt(j);
                    }
                }
                convertWorkBook(book, htmlDir + File.separator + fileNameWithout + "-" + i + ".html");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void convertWorkBook(HSSFWorkbook workBook, String htmlName) {
        //log.info("convertWorkBook htmlName:{}", htmlName);
        ByteArrayOutputStream outStream = null;
        try {
            outStream = new ByteArrayOutputStream();
            ExcelToHtmlConverter converter = new ExcelToHtmlConverter(DocumentBuilderFactory.
                    newInstance().newDocumentBuilder().newDocument());
            // 不显示列的表头
            converter.setOutputColumnHeaders(false);
            // 不显示行的表头
            converter.setOutputRowNumbers(false);
            converter.processWorkbook(workBook);
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(new DOMSource(converter.getDocument()), new StreamResult(outStream));
            String content = new String(outStream.toByteArray());
            FileUtils.writeStringToFile(new File(htmlName), content, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
