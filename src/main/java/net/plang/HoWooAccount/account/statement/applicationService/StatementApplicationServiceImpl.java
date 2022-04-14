package net.plang.HoWooAccount.account.statement.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import net.plang.HoWooAccount.account.statement.mapper.*;
import net.plang.HoWooAccount.account.statement.to.CashJournalBean;
import net.plang.HoWooAccount.account.statement.to.DetailTrialBalanceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatementApplicationServiceImpl implements StatementApplicationService {

    @Autowired
    private TotalTrialBalanceDAO totalTrialBalanceDAO;
    @Autowired
    private FinancialPositionDAO financialPositionDAO;
    @Autowired
    private IncomeStatementDAO IncomeStatementDAO;
    @Autowired
    private DetailTrialBalanceDAO detailTrialBalanceDAO;
    @Autowired
    private CashJournalDAO cashJournalDAO;


    @Override
    public void getTotalTrialBalance(HashMap<String, Object> param) {
        totalTrialBalanceDAO.callTotalTrialBalance(param);
    }

    @Override
    public HashMap<String, Object> getIncomeStatement(HashMap<String, Object> param) {
        return IncomeStatementDAO.callIncomeStatement(param);
    }

    @Override
    public HashMap<String, Object> getFinancialPosition(HashMap<String, Object> param) {
        return financialPositionDAO.callFinancialPosition(param);
    }

	@Override
	public ArrayList<DetailTrialBalanceBean> getDetailTrialBalance(HashMap<String, String> param) {
        return detailTrialBalanceDAO.selectDetailTrialBalance(param);
	}

	@Override
	public ArrayList<CashJournalBean> getCashJournal(HashMap<String, String> param) {
        return cashJournalDAO.selectCashJournalList(param);
	}

	@Override
	public HashMap<String, Object> getEarlyStatements(HashMap<String, Object> param) {
        return totalTrialBalanceDAO.callEarlyStatements(param);
    }

	@Override
	public HashMap<String, Object> changeAccountingSettlement(HashMap<String, Object> param) {
        return totalTrialBalanceDAO.updateAccountingSettlement(param);
    }
}
