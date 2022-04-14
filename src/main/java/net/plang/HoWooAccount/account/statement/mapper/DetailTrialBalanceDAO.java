package net.plang.HoWooAccount.account.statement.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import net.plang.HoWooAccount.account.statement.to.DetailTrialBalanceBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetailTrialBalanceDAO {

    public ArrayList<DetailTrialBalanceBean> selectDetailTrialBalance(HashMap<String, String> param);

}
