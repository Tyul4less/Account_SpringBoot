package net.plang.HoWooAccount.account.statement.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;


import net.plang.HoWooAccount.account.statement.applicationService.StatementApplicationService;
import net.plang.HoWooAccount.account.statement.to.CashJournalBean;
import net.plang.HoWooAccount.account.statement.to.DetailTrialBalanceBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StatementServiceFacadeImpl implements StatementServiceFacade {

    @Autowired
    private StatementApplicationService statementApplicationService;

    @Override
    public void getTotalTrialBalance(HashMap<String, Object> param) {
        statementApplicationService.getTotalTrialBalance(param);
    }

    @Override
    public HashMap<String, Object> getIncomeStatement(HashMap<String, Object> param) {
        return statementApplicationService.getIncomeStatement(param);
    }

    @Override
    public HashMap<String, Object> getFinancialPosition(HashMap<String, Object> param) {
        return statementApplicationService.getFinancialPosition(param);
    }

	@Override
	public ArrayList<DetailTrialBalanceBean> getDetailTrialBalance(HashMap<String, String> param) {
        return statementApplicationService.getDetailTrialBalance(param);
	}

	@Override
	public ArrayList<CashJournalBean> getCashJournal(HashMap<String, String> param) {
        return statementApplicationService.getCashJournal(param);
	}
    
	@Override
	public HashMap<String, Object> getEarlyStatements(HashMap<String, Object> param) {
        return statementApplicationService.getEarlyStatements(param);
    }

	@Override
	public HashMap<String, Object> changeAccountingSettlement(HashMap<String, Object> param) {
        return statementApplicationService.changeAccountingSettlement(param);
    }
}
