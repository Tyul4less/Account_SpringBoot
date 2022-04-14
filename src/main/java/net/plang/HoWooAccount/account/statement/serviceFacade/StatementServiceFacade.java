package net.plang.HoWooAccount.account.statement.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import net.plang.HoWooAccount.account.statement.to.CashJournalBean;
import net.plang.HoWooAccount.account.statement.to.DetailTrialBalanceBean;
import net.plang.HoWooAccount.account.statement.to.EarlyAssetBean;

public interface StatementServiceFacade {
	
/*	public HashMap<String, Object> getImTotalTrialBalance(String fromDate, String toDate);
	
	public HashMap<String, Object> getImFinancialStatement(String fromDate, String toDate);*/

    void getTotalTrialBalance(HashMap<String, Object> param);

    public HashMap<String, Object> getFinancialPosition(HashMap<String, Object> param);

    public HashMap<String, Object> getIncomeStatement(HashMap<String, Object> param);
    
    public ArrayList<DetailTrialBalanceBean> getDetailTrialBalance(HashMap<String, String> param);

    public ArrayList<CashJournalBean> getCashJournal(HashMap<String, String> param);

    public HashMap<String, Object> getEarlyStatements(HashMap<String, Object> param);
    
    public HashMap<String, Object> changeAccountingSettlement(HashMap<String, Object> param);

}

