package net.plang.HoWooAccount.account.slip.applicationService;

import net.plang.HoWooAccount.account.slip.to.JournalBean;
import net.plang.HoWooAccount.account.slip.to.SlipBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public interface SlipApplicationService {

    ArrayList<SlipBean> findSlipDataList(String slipDate);

    ArrayList<SlipBean> findRangedSlipList(HashMap<String, String> param);

    ArrayList<SlipBean> findDisApprovalSlipList();

    void addSlip(SlipBean slipBean, ArrayList<JournalBean> journalBeans);

    void deleteSlip(String slipNo);

    String updateSlip(SlipBean slipBean, ArrayList<JournalBean> journalBeans);

    void approveSlip(ArrayList<SlipBean> slipBeans);

    HashMap<String,Object> getAccountingSettlementStatus(HashMap<String, Object> param);

}
