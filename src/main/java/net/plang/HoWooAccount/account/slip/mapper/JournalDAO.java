package net.plang.HoWooAccount.account.slip.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import net.plang.HoWooAccount.account.slip.to.JournalBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JournalDAO {

     ArrayList<JournalBean> selectRangedJournalList(HashMap<String, String> param);

     ArrayList<JournalBean> selectJournalList(String slipNo);

     JournalBean selectJournal(String journalNo);

     String selectJournalName(String slipNo);

     void insertJournal(JournalBean journalBean);

     void deleteJournal(String journalNo);
    
     void deleteJournalAll(String slipNo);

     void updateJournal(JournalBean journalBean);
}
