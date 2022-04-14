package net.plang.HoWooAccount.system.base.serviceFacade;


import net.plang.HoWooAccount.hr.affair.to.EmployeeBean;
import net.plang.HoWooAccount.system.authority.applicationService.AuthorityApplicationService;
import net.plang.HoWooAccount.system.authority.to.AuthorityEmpBean;
import net.plang.HoWooAccount.system.base.applicationService.BaseApplicationService;
import net.plang.HoWooAccount.system.base.applicationService.BoardApplicationService;
import net.plang.HoWooAccount.system.base.applicationService.CodeListApplicationService;
import net.plang.HoWooAccount.system.base.exception.DeptCodeNotFoundException;
import net.plang.HoWooAccount.system.base.exception.IdNotFoundException;
import net.plang.HoWooAccount.system.base.exception.PwMissmatchException;
import net.plang.HoWooAccount.system.base.to.*;
import net.plang.HoWooAccount.system.common.exception.DataAccessException;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class BaseServiceFacadeImpl implements BaseServiceFacade {

    @Autowired
    private BaseApplicationService baseApplicationService;
    @Autowired
    private CodeListApplicationService codeListApplicationService;
    @Autowired
    private AuthorityApplicationService authorityApplicationService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private BoardApplicationService boardApplicationService;



    public void setEarlyStatements(String periodNo) {
        baseApplicationService.setEarlyStatements(periodNo);
    }


    public String getPeriodNo(String today) {
        return baseApplicationService.getPeriodNo(today);
    }


    public void insertPeriodNo(String sdate,String edate) {
        baseApplicationService.insertPeriodNo(sdate,edate);
    }

    //리포트 테스트중
    @Override
    public void getIreportData(HttpServletRequest request, HttpServletResponse response,String slipNo) {

        ArrayList<IreportBean> reportDataList = null;
        HashMap<String, Object> parameters = new HashMap<>();
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        try {

            Connection conn = dataSource.getConnection();

            parameters.put("slip_no", slipNo);

            JasperReport jasperReport = JasperCompileManager.compileReport((request.getServletContext().getRealPath("/resources/reportform/report1.jrxml")));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

            System.out.println("Ireport 시작");

            ServletOutputStream out = response.getOutputStream();

            response.setContentType("application/pdf");

            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    "D:\\dev\\InteliJWorkSpace\\Account4th\\Account_SpringBoot\\src\\main\\webapp\\resources\\PDF\\"
                            + slipNo + ".pdf");

            // 강제출력해서 화면에 보여지게됨
            out.flush();

        } catch (SQLException | IOException | JRException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public EmployeeBean getLoginData(String empCode, String userPw) throws IdNotFoundException, DeptCodeNotFoundException, PwMissmatchException {
        return baseApplicationService.getLoginData(empCode, userPw);
    }

    @Override
    public ArrayList<MenuBean> findUserMenuList(String deptCode) {
        return baseApplicationService.findMenuNameList(deptCode);
    }

    @Override
    public ArrayList<DetailCodeBean> getDetailCodeList(HashMap<String, String> param) {
        return codeListApplicationService.getDetailCodeList(param);
    }

    @Override
    public ArrayList<CodeBean> findCodeList() {
        return codeListApplicationService.findCodeList();
    }

    @Override
    public void batchCodeProcess(ArrayList<CodeBean> codeList, ArrayList<DetailCodeBean> codeList2) {
        codeListApplicationService.batchCodeProcess(codeList, codeList2);
    }

    @Override
    public ArrayList<AuthorityEmpBean> getAuthority(String empCode) {
        return authorityApplicationService.findAuthorityEmp(empCode);
    }


    //board
    @Override
    public ArrayList<BoardBean> findBoardList() {
        return boardApplicationService.findBoardList();
    }

    @Override
    public HashMap<String, Object> findPost(@RequestParam String boardNum) {
        return boardApplicationService.findPost(boardNum);
    }

    @Override
    public void registBoard(BoardBean bean) {
        boardApplicationService.registBoard(bean);
    };

    @Override
    public void boardFileUpload(ArrayList<BoardBean> arrayList){
        boardApplicationService.boardFileUpload(arrayList);
    }

    @Override
    public void updateBoard(BoardBean bean) {
        boardApplicationService.registBoard(bean);
    };

}