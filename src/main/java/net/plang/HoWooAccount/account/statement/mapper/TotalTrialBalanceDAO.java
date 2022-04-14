package net.plang.HoWooAccount.account.statement.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface TotalTrialBalanceDAO {
	
    public void callTotalTrialBalance(HashMap<String, Object> param);
    
    public HashMap<String, Object> callEarlyStatements(HashMap<String, Object> param);
        
    public HashMap<String, Object> updateAccountingSettlement(HashMap<String, Object> param);
   
}
