package net.plang.HoWooAccount.account.budget.applicationService;

import java.util.HashMap;
import net.plang.HoWooAccount.account.budget.to.BudgetBean;

public interface BudgetApplicationService {

	public HashMap<String, Object> findBudget(BudgetBean bean);
	
	public void orgBudget(BudgetBean bean);
	
	public HashMap<String, Object> findBudgetAppl(BudgetBean bean);
	
	public HashMap<String, Object> callBudgetStatus(BudgetBean bean);
	
}
