package net.plang.HoWooAccount.account.slip.mapper;

import java.util.ArrayList;

import net.plang.HoWooAccount.account.slip.to.JournalBean;
import net.plang.HoWooAccount.account.slip.to.JournalDetailBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JournalDetailDAO {

    ArrayList<JournalDetailBean> selectJournalDetailList(String journalNo);
    
    String selectJournalDetailDescriptionName(String journalDetailNo);

    void deleteJournalDetail(ArrayList<JournalBean> list);
    
    void deleteJournalDetailByJournalNo(String journalNo);

    void updateJournalDetail(JournalDetailBean journalDetailBean);

    void insertJournalDetailList(JournalDetailBean journalDetailBean);

}
