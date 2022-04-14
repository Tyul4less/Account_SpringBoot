package net.plang.HoWooAccount.account.statement.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import net.plang.HoWooAccount.account.statement.to.CashJournalBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CashJournalDAO {

    public ArrayList<CashJournalBean> selectCashJournalList(HashMap<String, String> param);

}
