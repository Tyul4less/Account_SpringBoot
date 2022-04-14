package net.plang.HoWooAccount.account.budget.mapper;

import java.util.HashMap;
import java.util.Vector;

import org.apache.ibatis.annotations.Mapper;

import net.plang.HoWooAccount.account.budget.to.BudgetBean;
import net.plang.HoWooAccount.account.budget.to.BudgetStatusBean;

@Mapper
public interface BudgetDAO {
	
	public BudgetBean selectBudget(BudgetBean bean);
	
	public void orgBudget(BudgetBean bean);
	
	public BudgetBean selectBudgetAppl(BudgetBean bean);
	
	public Vector<BudgetStatusBean> callBudgetStatus(HashMap<String, Object> params);
	
}
