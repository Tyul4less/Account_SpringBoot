package net.plang.HoWooAccount.account.statement.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.plang.HoWooAccount.account.statement.serviceFacade.StatementServiceFacade;
import net.plang.HoWooAccount.account.statement.serviceFacade.StatementServiceFacadeImpl;
import net.plang.HoWooAccount.account.statement.to.CashJournalBean;
import net.plang.HoWooAccount.system.common.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.mvc.AbstractController;

@RestController
@RequestMapping("/statement")
public class CashJournalController {

    @Autowired
    private StatementServiceFacade statementServiceFacade;

    @GetMapping("/cashJournal")
    public ArrayList<CashJournalBean> cashJournal(@RequestParam HashMap<String, String> param) {
        return statementServiceFacade.getCashJournal(param);
    }

}
