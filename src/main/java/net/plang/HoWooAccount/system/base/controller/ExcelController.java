package net.plang.HoWooAccount.system.base.controller;

import lombok.Setter;
import net.plang.HoWooAccount.system.base.serviceFacade.BaseServiceFacade;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class ExcelController  {

    @Autowired
    private BaseServiceFacade baseServiceFacade;

    @GetMapping("/base/createslipExcel")
    public String createslipExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav;
        ModelMap map = new ModelMap();

        //.xls 확장자 지원
        HSSFWorkbook wb = null;
        HSSFSheet sheet = null;
        Row row = null;
        Cell cell = null;

        //.xlsx 확장자 지원
        XSSFWorkbook xssfWb = null; // .xlsx
        XSSFSheet xssfSheet = null; // .xlsx
        XSSFRow xssfRow = null; // .xlsx
        XSSFCell xssfCell = null;// .xlsx

        try {
            System.out.println(request.getParameter("sendData"));
            String parameter = request.getParameter("sendData").replace("\\", "").replace("[", "").replace("]", "").replace("}\"", "}").replace("\"{", "{");
            System.out.println(parameter);
            JSONObject jsonObject = JSONObject.fromObject(parameter);
            System.out.println(jsonObject);
            int rowNo = 0; // 행 갯수
            // 워크북 생성
            xssfWb = new XSSFWorkbook();
            xssfSheet = xssfWb.createSheet("전표"); // 워크시트 이름

            //헤더용 폰트 스타일
            XSSFFont font = xssfWb.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL); //폰트스타일
            font.setFontHeightInPoints((short) 14); //폰트크기
            font.setBold(true); //Bold 유무

            //테이블 타이틀 스타일
            CellStyle cellStyle_Title = xssfWb.createCellStyle();
            cellStyle_Title.setBorderTop(BorderStyle.THIN); //테두리 위쪽
            cellStyle_Title.setBorderBottom(BorderStyle.THIN); //테두리 아래쪽
            cellStyle_Title.setBorderLeft(BorderStyle.THIN); //테두리 왼쪽
            cellStyle_Title.setBorderRight(BorderStyle.THIN); //테두리 오른쪽
            cellStyle_Title.setAlignment(HorizontalAlignment.CENTER);

            xssfSheet.setColumnWidth(0, (xssfSheet.getColumnWidth(3)) + (short) 2048);
            xssfSheet.setColumnWidth(1, (xssfSheet.getColumnWidth(3)) + (short) 2048);
            xssfSheet.setColumnWidth(2, (xssfSheet.getColumnWidth(3)) + (short) 2048);
            xssfSheet.setColumnWidth(3, (xssfSheet.getColumnWidth(3)) + (short) 2048); // 3번째 컬럼 넓이 조절
            xssfSheet.setColumnWidth(4, (xssfSheet.getColumnWidth(3)) + (short) 2048); // 4번째 컬럼 넓이 조절
            xssfSheet.setColumnWidth(5, (xssfSheet.getColumnWidth(3)) + (short) 2048); // 5번째 컬럼 넓이 조절
            xssfSheet.setColumnWidth(6, (xssfSheet.getColumnWidth(3)) + (short) 2048); // 8번째 컬럼 넓이 조절
            xssfSheet.setColumnWidth(7, (xssfSheet.getColumnWidth(3)) + (short) 2048);



            cellStyle_Title.setFont(font); // cellStle에 font를 적용
            cellStyle_Title.setAlignment(HorizontalAlignment.CENTER); // 정렬

            //셀병합
            xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7)); //첫행, 마지막행, 첫열, 마지막열( 0번째 행의 0~8번째 컬럼을 병합한다)
            //타이틀 생성
            xssfRow = xssfSheet.createRow(rowNo++); //행 객체 추가
            xssfCell = xssfRow.createCell((short) 0); // 추가한 행에 셀 객체 추가
            xssfCell.setCellStyle(cellStyle_Title); // 셀에 스타일 지정
            xssfCell.setCellValue("전표"); // 데이터 입력

            CellStyle cellStyle_Body = xssfWb.createCellStyle();
            cellStyle_Body.setAlignment(HorizontalAlignment.CENTER);

            //헤더 생성
            xssfRow = xssfSheet.createRow(rowNo++); //헤더 01
            xssfCell = xssfRow.createCell((short) 0);
            xssfCell.setCellStyle(cellStyle_Body);
            xssfCell.setCellValue("전표번호");
            xssfCell = xssfRow.createCell((short) 1);
            xssfCell.setCellStyle(cellStyle_Body);
            xssfCell.setCellValue("기수");
            xssfCell = xssfRow.createCell((short) 2);
            xssfCell.setCellStyle(cellStyle_Body);
            xssfCell.setCellValue("부서코드");
            xssfCell = xssfRow.createCell((short) 3);
            xssfCell.setCellStyle(cellStyle_Body);
            xssfCell.setCellValue("구분");
            xssfCell = xssfRow.createCell((short) 4);
            xssfCell.setCellStyle(cellStyle_Body);
            xssfCell.setCellValue("적요");
            xssfCell = xssfRow.createCell((short) 5);
            xssfCell.setCellStyle(cellStyle_Body);
            xssfCell.setCellValue("승인상태");
            xssfCell = xssfRow.createCell((short) 6);
            xssfCell.setCellStyle(cellStyle_Body);
            xssfCell.setCellValue("작성자코드");
            xssfCell = xssfRow.createCell((short) 7);
            xssfCell.setCellStyle(cellStyle_Body);
            xssfCell.setCellValue("작성일");


            int cnt = 1;
            while (true) {
                if (cnt > 1) {
                    System.out.println("반복문cnt진입" + parameter.indexOf(",{"));
                    if (parameter.indexOf(",{") != -1) {
                        System.out.println("문자열 자르기 진입");
                        parameter = parameter.substring(parameter.indexOf(",{") + 1, parameter.lastIndexOf("}") + 1);
                        System.out.println("자르기 결과" + parameter);
                        jsonObject = JSONObject.fromObject(parameter);
                    } else {
                        System.out.println("break진입");
                        break;
                    }
                }
                if (cnt > 1) {
                    System.out.println("반복한다" + cnt);
                }
                xssfRow = xssfSheet.createRow(rowNo++); //헤더 01
                xssfCell = xssfRow.createCell((short) 0);
                xssfCell.setCellStyle(cellStyle_Body);
                xssfCell.setCellValue(jsonObject.getString("slipNo"));
                xssfCell = xssfRow.createCell((short) 1);
                xssfCell.setCellStyle(cellStyle_Body);
                xssfCell.setCellValue(jsonObject.getString("accountPeriodNo"));
                xssfCell = xssfRow.createCell((short) 2);
                xssfCell.setCellStyle(cellStyle_Body);
                xssfCell.setCellValue(jsonObject.getString("deptCode"));
                xssfCell = xssfRow.createCell((short) 3);
                xssfCell.setCellStyle(cellStyle_Body);
                xssfCell.setCellValue(jsonObject.getString("slipType"));
                xssfCell = xssfRow.createCell((short) 4);
                xssfCell.setCellStyle(cellStyle_Body);
                xssfCell.setCellValue(jsonObject.getString("expenseReport"));
                xssfCell = xssfRow.createCell((short) 5);
                xssfCell.setCellStyle(cellStyle_Body);
                xssfCell.setCellValue(jsonObject.getString("slipStatus"));
                xssfCell = xssfRow.createCell((short) 6);
                xssfCell.setCellStyle(cellStyle_Body);
                xssfCell.setCellValue(jsonObject.getString("approvalEmpCode"));
                xssfCell = xssfRow.createCell((short) 7);
                xssfCell.setCellStyle(cellStyle_Body);
                xssfCell.setCellValue(jsonObject.getString("approvalDate"));


                //테이블 스타일 설정
                CellStyle cellStyle_Table_Center = xssfWb.createCellStyle();
                cellStyle_Table_Center.setBorderTop(BorderStyle.THIN); //테두리 위쪽
                cellStyle_Table_Center.setBorderBottom(BorderStyle.THIN); //테두리 아래쪽
                cellStyle_Table_Center.setBorderLeft(BorderStyle.THIN); //테두리 왼쪽
                cellStyle_Table_Center.setBorderRight(BorderStyle.THIN); //테두리 오른쪽
                cellStyle_Table_Center.setAlignment(HorizontalAlignment.CENTER);
                cnt++;
            }

            String localFile = "C:\\dev\\IntelliJ\\Account_IBATIS_WebLogic\\" + jsonObject.getString("slipNo") + ".xlsx";

            File file = new File(localFile);
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            xssfWb.write(fos);

            if (xssfWb != null) xssfWb.close();
            if (fos != null) fos.close();


            return jsonObject.getString("slipNo");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}