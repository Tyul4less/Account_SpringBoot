package net.plang.HoWooAccount.account.statement.controller;

import net.plang.HoWooAccount.account.statement.serviceFacade.StatementServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/statement")
public class IncomeStatementController {

    @Autowired
    private StatementServiceFacade statementServiceFacade;

    @GetMapping("/incomeStatement")
    public HashMap<String, Object> incomeStatement(@RequestParam String accountPeriodNo, @RequestParam String callResult) {
        System.out.println("accountPeriodNo = " + accountPeriodNo+callResult);
        HashMap<String, Object> param = new HashMap<>();
        param.put("accountPeriodNo", accountPeriodNo);
        param.put("callResult", callResult);
        statementServiceFacade.getIncomeStatement(param);
        System.out.println("incomeParam = " + param);
        return param;
    }
}

