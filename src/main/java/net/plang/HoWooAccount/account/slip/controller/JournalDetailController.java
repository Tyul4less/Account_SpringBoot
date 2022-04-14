package net.plang.HoWooAccount.account.slip.controller;


import net.plang.HoWooAccount.account.slip.serviceFacade.SlipServiceFacade;
import net.plang.HoWooAccount.account.slip.to.JournalDetailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/account")
public class JournalDetailController {

    @Autowired
    private SlipServiceFacade slipServiceFacade;

    @GetMapping("/getJournalDetailList")
    public ArrayList<JournalDetailBean> getJournalDetailList(@RequestParam String journalNo) {
        System.out.println("journalNo = " + journalNo);
        return slipServiceFacade.getJournalDetailList(journalNo);
    }

    @GetMapping("/editJournalDetail")
    public HashMap<String, String> editJournalDetail(@ModelAttribute JournalDetailBean journalDetailBean) {

        HashMap<String, String> map = new HashMap<>();
        String accountControlType = journalDetailBean.getAccountControlType();
        String journalDescription;

        journalDetailBean.setAccountControlType(accountControlType);

        String dName = slipServiceFacade.editJournalDetail(journalDetailBean);

        Boolean findSelect = accountControlType.equals("SELECT");
        Boolean findSearch = accountControlType.equals("SEARCH");

        if(findSelect || findSearch) {
            journalDescription=dName;

            map.put("accountControlType", accountControlType);
            map.put("journalDescription", journalDescription);
        }

        return map;
    }
}
