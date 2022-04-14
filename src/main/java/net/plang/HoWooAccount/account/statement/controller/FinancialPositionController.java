package net.plang.HoWooAccount.account.statement.controller;

import lombok.Setter;
import net.plang.HoWooAccount.account.statement.serviceFacade.StatementServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/statement")
public class FinancialPositionController {

    @Autowired
    private StatementServiceFacade statementServiceFacade;

    @GetMapping("/financialPosition")
    public HashMap<String, Object> financialPosition(@RequestParam String accountPeriodNo, @RequestParam String callResult) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("accountPeriodNo", accountPeriodNo);
        param.put("callResult", callResult);
        statementServiceFacade.getFinancialPosition(param);
        return param;
    }
}

