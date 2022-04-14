package net.plang.HoWooAccount.account.statement.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import net.plang.HoWooAccount.account.statement.to.CashJournalBean;
import net.plang.HoWooAccount.account.statement.to.DetailTrialBalanceBean;
import net.plang.HoWooAccount.account.statement.to.EarlyAssetBean;

public interface StatementApplicationService {

    void getTotalTrialBalance(HashMap<String, Object> param);

    HashMap<String, Object> getIncomeStatement(HashMap<String, Object> param);

    HashMap<String, Object> getFinancialPosition(HashMap<String, Object> param);

    ArrayList<DetailTrialBalanceBean> getDetailTrialBalance(HashMap<String, String> param);

    ArrayList<CashJournalBean> getCashJournal(HashMap<String, String> param);

    HashMap<String, Object> getEarlyStatements(HashMap<String, Object> param);

/*    HashMap<String, Object> getImTotalTrialBalance(String fromDate, String toDate);

    HashMap<String, Object> getImFinancialStatement(String fromDate, String toDate);*/

    HashMap<String, Object> changeAccountingSettlement(HashMap<String, Object> param);

}
