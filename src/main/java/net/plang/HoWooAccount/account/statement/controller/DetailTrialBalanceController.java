package net.plang.HoWooAccount.account.statement.controller;

import net.plang.HoWooAccount.account.statement.serviceFacade.StatementServiceFacade;
import net.plang.HoWooAccount.account.statement.to.DetailTrialBalanceBean;
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
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/statement")
public class DetailTrialBalanceController {

    @Autowired
    private StatementServiceFacade statementServiceFacade;

    @GetMapping("/detailTrialBalance")
    public ArrayList<DetailTrialBalanceBean> detailTrialBalance (@RequestParam HashMap<String, String> param) {
        return statementServiceFacade.getDetailTrialBalance(param);
    }

}
