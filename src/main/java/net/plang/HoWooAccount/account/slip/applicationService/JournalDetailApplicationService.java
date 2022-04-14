package net.plang.HoWooAccount.account.slip.applicationService;

import java.util.ArrayList;

import net.plang.HoWooAccount.account.slip.to.JournalDetailBean;

public interface JournalDetailApplicationService {
    public ArrayList<JournalDetailBean> getJournalDetailList(String journalNo);

    public String editJournalDetail(JournalDetailBean journalDetailBean);
}
