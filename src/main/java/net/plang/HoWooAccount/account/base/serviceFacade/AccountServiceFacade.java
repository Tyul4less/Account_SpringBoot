package net.plang.HoWooAccount.account.base.serviceFacade;

import java.util.ArrayList;

import net.plang.HoWooAccount.account.base.to.AccountBean;
import net.plang.HoWooAccount.account.base.to.AccountControlBean;
import net.plang.HoWooAccount.account.base.to.PeriodBean;

public interface AccountServiceFacade {

    public AccountBean getAccount(String code);

    public ArrayList<AccountBean> findParentAccountList();

    public ArrayList<AccountBean> findDetailAccountList(String code);

    public void updateAccount(AccountBean accountBean);

    ArrayList<AccountBean> getAccountListByName(String accountName);

    ArrayList<AccountControlBean> getAccountControlList(String accountCode);

    public ArrayList<AccountBean> findDetailBudgetList(String code);
    
    public ArrayList<AccountBean> findParentBudgetList();
    
    public ArrayList<PeriodBean> findAccountPeriodList();
}
