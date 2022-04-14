package net.plang.HoWooAccount.account.slip.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import net.plang.HoWooAccount.account.slip.to.JournalBean;
import net.plang.HoWooAccount.account.slip.to.JournalDetailBean;
import net.plang.HoWooAccount.account.slip.to.SlipBean;
import net.plang.HoWooAccount.account.statement.to.AccountingSettlementStatusBean;

public interface SlipServiceFacade {
    public ArrayList<JournalDetailBean> getJournalDetailList(String journalNo);

    public String editJournalDetail(JournalDetailBean journalDetailBean);
    
    public ArrayList<JournalBean> findRangedJournalList(HashMap<String, String> param);

    public ArrayList<JournalBean> findSingleJournalList(String slipNo);

    public void deleteJournal(String journalNo);

    public ArrayList<SlipBean> findSlipDataList(String slipDate);

    public ArrayList<SlipBean> findRangedSlipList(HashMap<String, String> param);

    public ArrayList<SlipBean> findDisApprovalSlipList();

    public void addSlip(SlipBean slipBean, ArrayList<JournalBean> journalBeans);

    public void deleteSlip(String slipNo);

    public String updateSlip(SlipBean slipBean, ArrayList<JournalBean> journalBeans);

    public void approveSlip(ArrayList<SlipBean> slipBeans);
    
    public HashMap<String,Object> getAccountingSettlementStatus(HashMap<String, Object> param);
    
}

