package net.plang.HoWooAccount.account.base.controller;

import lombok.Setter;
import net.plang.HoWooAccount.account.base.serviceFacade.AccountServiceFacade;
import net.plang.HoWooAccount.account.base.to.AccountBean;
import net.plang.HoWooAccount.account.base.to.AccountControlBean;
import net.plang.HoWooAccount.account.base.to.PeriodBean;
import net.plang.HoWooAccount.system.common.exception.DataAccessException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountServiceFacade accountServiceFacade;

    @GetMapping("/getAccount")
    public AccountBean getAccount(@RequestParam String accountCode) {
        return accountServiceFacade.getAccount(accountCode);
    }

    @GetMapping("/getAccountControlList")
    public ArrayList<AccountControlBean> getAccountControlList(@RequestParam String accountCode) {
        return accountServiceFacade.getAccountControlList(accountCode);
    }

    @GetMapping("/getAccountListByName")
    public ArrayList<AccountBean> getAccountListByName(@RequestParam String accountName) {
        System.out.println("accountName = " + accountName);
        return accountServiceFacade.getAccountListByName(accountName);
    }

    @GetMapping("/findParentAccountList")
    public ArrayList<AccountBean> findParentAccountList() {
        return accountServiceFacade.findParentAccountList();
    }

    @GetMapping("/findDetailAccountList")
    public ArrayList<AccountBean> findDetailAccountList(@RequestParam String code) {
        return accountServiceFacade.findDetailAccountList(code);
    }

    @GetMapping("/findDetailBudgetList")
    public ArrayList<AccountBean> findDetailBudgetList(@RequestParam String code) {
        return accountServiceFacade.findDetailBudgetList(code);
    }

    @GetMapping("/findParentBudgetList")
    public ArrayList<AccountBean> findParentBudgetList() {
        return accountServiceFacade.findParentBudgetList();
    }

    @GetMapping("/findAccountPeriodList")
    public ArrayList<PeriodBean> findAccountPeriodList() {
        return accountServiceFacade.findAccountPeriodList();
    }
}
