package net.plang.HoWooAccount.account.budget.serviceFacade;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.plang.HoWooAccount.account.budget.applicationService.BudgetApplicationService;
import net.plang.HoWooAccount.account.budget.to.BudgetBean;

@Service
public class BudgetServiceFacadeImpl implements BudgetServiceFacade{
	
	@Autowired
	private BudgetApplicationService budgetApplicationService;
		    
	@Override
	public HashMap<String, Object> findBudget(BudgetBean bean) {
		
		return budgetApplicationService.findBudget(bean);
	}

	@Override
	public void orgBudget(BudgetBean bean) {

		budgetApplicationService.orgBudget(bean);
	}

	@Override
	public HashMap<String, Object> callBudgetStatus(BudgetBean bean) {
	
		return budgetApplicationService.callBudgetStatus(bean);
	}

	@Override
	public HashMap<String, Object> findBudgetAppl(BudgetBean bean) {
		 
		return budgetApplicationService.findBudgetAppl(bean);
	}
}
