package net.plang.HoWooAccount.account.base.applicationService;

import java.util.ArrayList;

import lombok.Setter;

import net.plang.HoWooAccount.account.base.mapper.AccountDAO;
import net.plang.HoWooAccount.account.base.to.AccountBean;
import net.plang.HoWooAccount.account.base.to.AccountControlBean;
import net.plang.HoWooAccount.account.base.to.PeriodBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountApplicationServiceImpl implements AccountApplicationService {

	@Autowired
	private AccountDAO accountDAO;

	public AccountBean getAccount(String accountCode) {
		return accountDAO.selectAccount(accountCode);
	}

	@Override
	public ArrayList<AccountBean> findParentAccountList() {
		return accountDAO.selectParentAccountList();
	}

	public ArrayList<AccountBean> findDetailAccountList(String code) {
		return accountDAO.selectDetailAccountList(code);
	}

	@Override
	public void updateAccount(AccountBean accountBean) {

	}

	@Override
	public ArrayList<AccountBean> getAccountListByName(String accountName) {
		return accountDAO.selectAccountListByName(accountName);
	}

	@Override
	public ArrayList<AccountControlBean> getAccountControlList(String accountCode) {
		return accountDAO.selectAccountControlList(accountCode);
	}

	@Override
	public ArrayList<AccountBean> findDetailBudgetList(String code) {
		return accountDAO.selectDetailBudgetList(code);
	}

	@Override
	public ArrayList<AccountBean> findParentBudgetList() {
		return accountDAO.selectParentBudgetList();
	}

	@Override
	public ArrayList<PeriodBean> findAccountPeriodList() {
		return accountDAO.selectAccountPeriodList();
	}

}
