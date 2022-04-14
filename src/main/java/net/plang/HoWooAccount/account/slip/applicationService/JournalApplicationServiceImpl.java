package net.plang.HoWooAccount.account.slip.applicationService;

import net.plang.HoWooAccount.account.slip.mapper.JournalDAO;
import net.plang.HoWooAccount.account.slip.mapper.JournalDetailDAO;
import net.plang.HoWooAccount.account.slip.to.JournalBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class JournalApplicationServiceImpl implements JournalApplicationService {

    @Autowired
    private JournalDAO journalDAO;
    @Autowired
    private JournalDetailDAO journalDetailDAO;

    @Override
    public ArrayList<JournalBean> findRangedJournalList(HashMap<String, String> param) {
        return journalDAO.selectRangedJournalList(param);
    }

    @Override
    public void deleteJournal(String journalNo) {
        journalDAO.deleteJournal(journalNo);
        journalDetailDAO.deleteJournalDetailByJournalNo(journalNo);
    }

    @Override
    public ArrayList<JournalBean> findSingleJournalList(String slipNo) {
        return journalDAO.selectJournalList(slipNo);
    }


}
