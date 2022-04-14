package net.plang.HoWooAccount.system.base.controller;

import lombok.Setter;
import net.plang.HoWooAccount.system.base.serviceFacade.BaseServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Properties;

@RestController
@RequestMapping("/base")
public class NaverEmailController {

    @Autowired
    BaseServiceFacade baseServiceFacade;

    @GetMapping("/naverEmail")
    public ModelAndView naverEmail(HttpServletRequest request, HttpServletResponse response, @RequestParam String eMail, @RequestParam String slipNo) throws Exception{
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@?");
        Multipart multipart = null;

        System.out.println("eMail = " + eMail);
        System.out.println("slipNo = " + slipNo);

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("slip_no", slipNo);


        String FROM = "";
        String FROMNAME = "ABSTERGO";
        String SMTP_USERNAME = "";
        String SMTP_PASSWORD = "";

        baseServiceFacade.getIreportData(request, response, slipNo);

/*        System.out.println("제스퍼전");
        JasperReport jasperReport = JasperCompileManager.compileReport((request.getServletContext().getRealPath("/resources/reportform/report1.jrxml")));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
        JasperExportManager.exportReportToPdfFile(
                jasperPrint, "D:\\dev\\InteliJWorkSpace\\Account3rd\\Account_IBATIS_Excel\\src\\main\\webapp\\resources\\PDF\\test.pdf");

        System.out.println("제스퍼후");*/


        String HOST = "smtp.naver.com";
        int PORT = 587;
//	    String SUBJECT = "Abstergo Industries 재직증명서";

        String BODY = String.join(
                System.getProperty("line.separator"),
                "<h1>Abstergo Industries 회계전표</h1>",
                "<p>요청하신 회계전표를 발송하였습니다.</p>."
        );
        Properties props = System.getProperties();
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

//	        props.put("mail.smtp.host", HOST);
//	        props.put("mail.smtp.ssl.enable", "true");
//	        props.put("mail.smtp.ssl.trust", HOST);

        Session session = Session.getDefaultInstance(props);

        MimeMessage msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(FROM, FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(eMail));
        msg.setSubject("Abstergo Industries 회계전표");

//	        msg.setSubject(SUBJECT);

        multipart = new MimeMultipart();
        MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText("요청하신 회계전표를 발송하였습니다.");
        multipart.addBodyPart(mbp1);

        DataSource source = new FileDataSource(
                "D:\\dev\\InteliJWorkSpace\\Account4th\\Account_SpringBoot\\src\\main\\webapp\\resources\\PDF\\"+slipNo+".pdf");
        BodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("Proof.pdf");
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);

//	        msg.setContent(BODY, "text/html;charset=euc-kr");

        try (Transport transport = session.getTransport()) {
            System.out.println("Sending...");

            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
//	        msg.setSubject(SUBJECT);
}
