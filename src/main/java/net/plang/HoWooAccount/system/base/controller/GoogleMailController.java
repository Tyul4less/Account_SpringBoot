package net.plang.HoWooAccount.system.base.controller;

import lombok.Setter;
import net.plang.HoWooAccount.system.base.to.IreportBean;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/base")
public class GoogleMailController {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private DataSource dataSource;

    @GetMapping("/googleEmail")
    public ModelAndView googleEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("이메일 = " + request.getParameter("eMail"));
        System.out.println("전표 = " + request.getParameter("slipNo"));
        String email = request.getParameter("eMail");
        String slipNo = request.getParameter("slipNo");
        String subject = "test 메일";
        String content = "메일 테스트 내용";
        String from = "";
        String to = email;
        try {

            getIreportData(request, response,slipNo);
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(mail,true,"UTF-8");

            mailHelper.setFrom(from);
            mailHelper.setTo(to);
            mailHelper.setSubject(subject);
            mailHelper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File("D:\\dev\\InteliJWorkSpace\\Account3rd\\Account_IBATIS_Excel\\src\\main\\webapp\\resources\\PDF\\"+slipNo+".pdf"));
            mailHelper.addAttachment(slipNo+".pdf", file);

            mailSender.send(mail);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<IreportBean> getIreportData(HttpServletRequest request, HttpServletResponse response, String slipNo) {

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
                    "D:\\dev\\InteliJWorkSpace\\Account3rd\\Account_IBATIS_Excel\\src\\main\\webapp\\resources\\PDF\\"
                            + slipNo + ".pdf");

            // 강제출력해서 화면에 보여지게됨
            out.flush();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (JRException jrException) {
            jrException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }
}