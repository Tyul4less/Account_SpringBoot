package net.plang.HoWooAccount.account.slip.applicationService;

import net.plang.HoWooAccount.account.slip.mapper.JournalDetailDAO;
import net.plang.HoWooAccount.account.slip.to.JournalDetailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JournalDetailApplicationServiceImpl implements JournalDetailApplicationService {

	@Autowired
	private JournalDetailDAO journalDetailDAO;

	@Override
	public ArrayList<JournalDetailBean> getJournalDetailList(String journalNo) {
		return journalDetailDAO.selectJournalDetailList(journalNo);
	}

	
	@Override
	public String editJournalDetail(JournalDetailBean journalDetailBean) {
		String dName=null;
		String journalDetailNo = journalDetailBean.getJournalDetailNo();
		String accountControlType = journalDetailBean.getAccountControlType();
		Boolean findSelect = accountControlType.equals("SELECT");
		Boolean findSearch = accountControlType.equals("SEARCH");

		journalDetailDAO.updateJournalDetail(journalDetailBean);
		if(findSelect || findSearch)
		dName = journalDetailDAO.selectJournalDetailDescriptionName(journalDetailNo);
		return dName;
	}
	
}
