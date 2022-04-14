package net.plang.HoWooAccount.account.slip.controller;

import net.plang.HoWooAccount.account.slip.serviceFacade.SlipServiceFacade;
import net.plang.HoWooAccount.account.slip.to.JournalBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/account")
public class JournalController {

    @Autowired
    private SlipServiceFacade slipServiceFacade;

    @GetMapping("/findRangedJournalList")
    public ArrayList<JournalBean> findRangedJournalList(@RequestParam String from, @RequestParam String to) {
        HashMap<String, String> param = new HashMap<>();
        param.put("fromDate", from);
        param.put("toDate", to);
        return slipServiceFacade.findRangedJournalList(param);
    }

    @GetMapping("/findSingleJournalList")
    public ArrayList<JournalBean> findSingleJournalList(@RequestParam String slipNo){
        ArrayList<JournalBean> result = slipServiceFacade.findSingleJournalList(slipNo);
        return result;
    }

    @GetMapping("/deleteJournal")
    public void deleteJournal(@RequestParam String journalNo) {
        slipServiceFacade.deleteJournal(journalNo);
    }
  
}
