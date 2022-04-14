package net.plang.HoWooAccount.account.base.mapper;

import java.util.ArrayList;

import net.plang.HoWooAccount.account.base.to.AccountBean;
import net.plang.HoWooAccount.account.base.to.AccountControlBean;
import net.plang.HoWooAccount.account.base.to.PeriodBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountDAO {

    public AccountBean selectAccount(String accountCode);

    public ArrayList<AccountBean> selectDetailAccountList(String code);

    public ArrayList<AccountBean> selectParentAccountList();

    public ArrayList<AccountBean> selectAccountListByName(String accountName);

    public ArrayList<AccountControlBean> selectAccountControlList(String accountCode);

    public ArrayList<AccountBean> selectDetailBudgetList(String code);
    
    public ArrayList<AccountBean> selectParentBudgetList();
    
    public ArrayList<PeriodBean> selectAccountPeriodList();
    
}
