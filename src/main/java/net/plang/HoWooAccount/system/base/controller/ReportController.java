package net.plang.HoWooAccount.system.base.controller;

import net.plang.HoWooAccount.system.base.serviceFacade.BaseServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/base")
public class ReportController {

    @Autowired
    private BaseServiceFacade baseServiceFacade;


    @GetMapping("/FinancialPosition")
    public void FinancialPosition(HttpServletRequest request, HttpServletResponse response, @RequestParam String slipNo) {
        System.out.println("전표 아이리포트 시작");

        HashMap<String, Object> parameters = new HashMap<>();
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        parameters.put("slip_no", slipNo);
        baseServiceFacade.getIreportData(request, response, slipNo);
    }

}