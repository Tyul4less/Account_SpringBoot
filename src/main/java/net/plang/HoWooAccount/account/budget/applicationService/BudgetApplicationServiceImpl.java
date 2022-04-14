package net.plang.HoWooAccount.account.budget.applicationService;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.plang.HoWooAccount.account.budget.mapper.BudgetDAO;
import net.plang.HoWooAccount.account.budget.to.BudgetBean;

@Component
public class BudgetApplicationServiceImpl implements BudgetApplicationService{

	@Autowired
	private BudgetDAO budgetDAO;
		
	@Override
	public HashMap<String, Object> findBudget(BudgetBean bean) {

		bean = budgetDAO.selectBudget(bean);
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("budgeBean", bean);
		map.put("errorCode", 0);
		map.put("errorMsg", "편성 예산 조회 완료");
		
		return map;
	}

	@Override
	public void orgBudget(BudgetBean bean) {

		budgetDAO.orgBudget(bean);
	}

	@Override
	public HashMap<String, Object> callBudgetStatus(BudgetBean bean) {

		HashMap<String,Object> params = new HashMap<String,Object>();
		
		params.put("accountPeriodNo", bean.getAccountPeriodNo());
		params.put("workplaceCode", bean.getWorkplaceCode());
		params.put("deptCode", bean.getDeptCode());
		
		budgetDAO.callBudgetStatus(params);

		return params;
	}

	@Override
	public HashMap<String, Object> findBudgetAppl(BudgetBean bean) {
		
		bean = budgetDAO.selectBudgetAppl(bean);
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("budgetBean", bean);
		map.put("errorCode", 0);
		map.put("errorMsg", "예산 신청 조회 완료");
		
	return map;
	}

}
