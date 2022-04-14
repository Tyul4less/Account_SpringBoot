package net.plang.HoWooAccount.account.budget.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.plang.HoWooAccount.account.budget.serviceFacade.BudgetServiceFacade;
import net.plang.HoWooAccount.account.budget.to.BudgetBean;
import com.google.gson.Gson;
import net.sf.json.JSONObject;

@RestController
@RequestMapping(value="/account")
public class BudgetController {
	
	@Autowired
    private BudgetServiceFacade budgetServiceFacade;
 
	@RequestMapping(value="/findBudget", method = RequestMethod.GET)
    public HashMap<String, Object> findBudget(HttpServletRequest request) {

		Gson gson = new Gson();
		JSONObject budgetObj = JSONObject.fromObject(request.getParameter("budgetObj")); //예산
		BudgetBean budgetBean = gson.fromJson(budgetObj.toString(), BudgetBean.class);
		
		return budgetServiceFacade.findBudget(budgetBean);
	 }
	
	@RequestMapping(value="/orgBudget", method = RequestMethod.PUT) 
	public void orgBudget(HttpServletRequest request) {

		Gson gson = new Gson();
		JSONObject budgetObj = JSONObject.fromObject(request.getParameter("budgetObj")); //예산
		BudgetBean budgetBean = gson.fromJson(budgetObj.toString(), BudgetBean.class);
		 
		budgetServiceFacade.orgBudget(budgetBean);
	}

	@RequestMapping(value="/callBudgetStatus", method = RequestMethod.GET) 
	public HashMap<String, Object> callBudgetStatus(HttpServletRequest request) {

		Gson gson = new Gson();
		JSONObject budgetObj = JSONObject.fromObject(request.getParameter("budgetObj")); //예산
		BudgetBean budgetBean = gson.fromJson(budgetObj.toString(), BudgetBean.class);

	return budgetServiceFacade.callBudgetStatus(budgetBean);
	}
	
	@RequestMapping(value="/findBudgetAppl", method = RequestMethod.GET)
		public HashMap<String, Object> findBudgetAppl(HttpServletRequest request) {

		Gson gson = new Gson();
		JSONObject budgetObj = JSONObject.fromObject(request.getParameter("budgetObj")); //예산
		BudgetBean budgetBean = gson.fromJson(budgetObj.toString(), BudgetBean.class);

		return budgetServiceFacade.findBudgetAppl(budgetBean);
	}
}
