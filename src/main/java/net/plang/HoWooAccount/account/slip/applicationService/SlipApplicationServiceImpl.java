package net.plang.HoWooAccount.account.slip.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import net.plang.HoWooAccount.account.slip.mapper.JournalDAO;
import net.plang.HoWooAccount.account.slip.mapper.JournalDetailDAO;
import net.plang.HoWooAccount.account.slip.mapper.SlipDAO;
import net.plang.HoWooAccount.account.slip.to.JournalBean;
import net.plang.HoWooAccount.account.slip.to.JournalDetailBean;
import net.plang.HoWooAccount.account.slip.to.SlipBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SlipApplicationServiceImpl implements SlipApplicationService {

    @Autowired
    private SlipDAO slipDAO;
    @Autowired
    private JournalDAO journalDAO;
    @Autowired
    private JournalDetailDAO journalDetailDAO;

    @Override
    public ArrayList<SlipBean> findSlipDataList(String slipDate) {
        return slipDAO.selectSlipDataList(slipDate);
    }

    @Override
    public void addSlip(SlipBean slipBean, ArrayList<JournalBean> journalBeans) {

        StringBuffer slipNo = new StringBuffer();
        String slipNoDate = slipBean.getReportingDate().replace("-", ""); // 2021-10-27 -> 20211027
        System.out.println("slipNoDate = " + slipNoDate);
        slipNo.append(slipNoDate); //20200118
        slipNo.append("SLIP"); // 20200118SLIP
        String code = "0000" + (slipDAO.selectSlipCount(slipNoDate) + 1) + ""; // 00001
        System.out.println("code = " + code);
        slipNo.append(code.substring(code.length() - 5)); // 00001
        System.out.println("slipNoDate = " + slipNo);

        slipBean.setSlipNo(slipNo.toString()); //20201111SLIP00001
        slipDAO.insertSlip(slipBean);
        for (JournalBean journalBean : journalBeans) {
            System.out.println(journalBean);
            String journalNo = journalDAO.selectJournalName(slipBean.getSlipNo());
            System.out.println("journalNo1 = " + journalNo);
            journalBean.setSlipNo(slipBean.getSlipNo());
            journalBean.setJournalNo(journalNo);
            journalDAO.insertJournal(journalBean);
            System.out.println("journalNo2 = " + journalNo);



            if(journalBean.getJournalDetailList()!=null) {
                for (JournalDetailBean journalDetailBean : journalBean.getJournalDetailList()) {
                    journalDetailBean.setJournalNo(journalNo);
                    System.out.println("journalDetailBean.getJournalNo() = " + journalDetailBean.getJournalNo());
                    journalDetailDAO.insertJournalDetailList(journalDetailBean);
                }
            }
        }
    }

    @Override
    public void deleteSlip(String slipNo) {

        ArrayList<JournalBean> list = journalDAO.selectJournalList(slipNo);
        System.out.println("list = " + list);
        slipDAO.deleteSlip(slipNo);
        System.out.println("전표삭제했음");
        journalDAO.deleteJournalAll(slipNo);
        System.out.println("분개삭제했음");
        System.out.println("list.size() = " + list.size());
        if(list.size()!=0)
        journalDetailDAO.deleteJournalDetail(list);
        System.out.println("분개상세삭제했음");
    }

    @Override
    public String updateSlip(SlipBean slipBean, ArrayList<JournalBean> journalBeans) {

        slipDAO.updateSlip(slipBean);
        System.out.println("전표업데이트");


        for (JournalBean journalBean : journalBeans) {
            journalDAO.updateJournal(journalBean);
            System.out.println("분개업데이트");
        if(journalBean.getJournalDetailList()!=null)
            for(JournalDetailBean journalDetailBean: journalBean.getJournalDetailList()) {
                journalDetailDAO.updateJournalDetail(journalDetailBean);
                System.out.println("분개상세업데이트");
            }
        }
        return slipBean.getSlipNo();
    }

    @Override
    public void approveSlip(ArrayList<SlipBean> slipBeans) {
            for (SlipBean slipBean : slipBeans) {
                slipBean.setSlipStatus(slipBean.getSlipStatus().equals("true") ? "승인완료" : "작성중(반려)");
                slipDAO.approveSlip(slipBean);
            }
    }

    @Override
    public ArrayList<SlipBean> findRangedSlipList(HashMap<String, String> param) {
        System.out.println("출발");

        ArrayList<SlipBean> result = slipDAO.selectRangedSlipList(param);

        System.out.println("도착");
        return result;
    }

    @Override
    public ArrayList<SlipBean> findDisApprovalSlipList() {
        return slipDAO.selectDisApprovalSlipList();
    }

	@Override
	public HashMap<String,Object> getAccountingSettlementStatus(HashMap<String, Object> param) {
        return slipDAO.callAccountingSettlementStatus(param);
	}
}


