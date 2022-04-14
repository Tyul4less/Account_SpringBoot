package net.plang.HoWooAccount.account.statement.controller;

import com.google.gson.Gson;
import net.plang.HoWooAccount.account.statement.serviceFacade.StatementServiceFacade;
import net.plang.HoWooAccount.system.common.exception.DataAccessException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;

@RestController
@RequestMapping("/statement")
public class TotalTrialBalanceController {

    @Autowired
    private StatementServiceFacade statementServiceFacade;

    @GetMapping("/doClosing")
    public HashMap<String, Object> doClosing(@RequestParam String accountPeriodNo, @RequestParam String callResult) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("accountPeriodNo", accountPeriodNo);
        param.put("callResult", callResult);
        statementServiceFacade.getEarlyStatements(param);
        System.out.println("ClosingParam = " + param);
        return param;
    }

    @GetMapping("/showTotalTrialBalance")
    public HashMap<String, Object> showTotalTrialBalance(@RequestParam String accountPeriodNo, @RequestParam String callResult) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("accountPeriodNo", accountPeriodNo);
        param.put("callResult", callResult);
        statementServiceFacade.getTotalTrialBalance(param);
        return param;
    }

    @GetMapping("/cancelClosing")
    public HashMap<String, Object> cancelClosing(@RequestParam String accountPeriodNo, @RequestParam String callResult) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("accountPeriodNo", accountPeriodNo);
        param.put("callResult", callResult);
        statementServiceFacade.changeAccountingSettlement(param);
        System.out.println("ClosingParam = " + param);
        return param;
    }

}






