package net.plang.HoWooAccount.account.slip.serviceFacade;

import net.plang.HoWooAccount.account.slip.applicationService.JournalApplicationService;
import net.plang.HoWooAccount.account.slip.applicationService.JournalDetailApplicationService;
import net.plang.HoWooAccount.account.slip.applicationService.SlipApplicationService;
import net.plang.HoWooAccount.account.slip.to.JournalBean;
import net.plang.HoWooAccount.account.slip.to.JournalDetailBean;
import net.plang.HoWooAccount.account.slip.to.SlipBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class SlipServiceFacadeImpl implements SlipServiceFacade {

    @Autowired
    private JournalDetailApplicationService journalDetailApplicationService;
    @Autowired
    private JournalApplicationService journalApplicationService;
    @Autowired
    private SlipApplicationService slipApplicationService;

    @Override
    public String editJournalDetail(JournalDetailBean journalDetailBean) {
        return journalDetailApplicationService.editJournalDetail(journalDetailBean);
    }

    @Override
    public ArrayList<JournalDetailBean> getJournalDetailList(String journalNo) {
        return journalDetailApplicationService.getJournalDetailList(journalNo);
    }
    
    @Override
    public ArrayList<JournalBean> findRangedJournalList(HashMap<String, String> param) {
        return journalApplicationService.findRangedJournalList(param);
    }

    @Override
    public ArrayList<JournalBean> findSingleJournalList(String slipNo) {
        return journalApplicationService.findSingleJournalList(slipNo);
    }
    
    @Override
    public void deleteJournal(String journalNo) {
        journalApplicationService.deleteJournal(journalNo);
    }

    @Override
    public void addSlip(SlipBean slipBean, ArrayList<JournalBean> journalBeans) {
        slipApplicationService.addSlip(slipBean, journalBeans);
    }

    
    @Override
    public void deleteSlip(String slipNo) {
        slipApplicationService.deleteSlip(slipNo);
    }

    @Override
    public String updateSlip(SlipBean slipBean, ArrayList<JournalBean> journalBeans) {
        return slipApplicationService.updateSlip(slipBean, journalBeans);
    }

    @Override
    public void approveSlip(ArrayList<SlipBean> slipBeans) {
            slipApplicationService.approveSlip(slipBeans);
    }

    @Override
    public ArrayList<SlipBean> findSlipDataList(String slipDate) {
        return slipApplicationService.findSlipDataList(slipDate);
    }

    @Override
    public ArrayList<SlipBean> findRangedSlipList(HashMap<String, String> param) {
        System.out.println("findRangedSlipList return");
        return slipApplicationService.findRangedSlipList(param);
    }

    @Override
    public ArrayList<SlipBean> findDisApprovalSlipList() {
        return slipApplicationService.findDisApprovalSlipList();
    }

	@Override
	public HashMap<String,Object> getAccountingSettlementStatus(HashMap<String, Object> param) {
        return slipApplicationService.getAccountingSettlementStatus(param);
	}
}
