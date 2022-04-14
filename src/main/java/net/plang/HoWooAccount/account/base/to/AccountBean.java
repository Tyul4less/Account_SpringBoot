package net.plang.HoWooAccount.account.base.to;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.plang.HoWooAccount.system.base.to.BaseBean;

import java.util.ArrayList;

@Setter
@Getter
public class AccountBean extends BaseBean {
    private ArrayList<AccountControlBean> accountControlList;
    private String accountInnerCode;
    private String parentAccountInnercode;
    private String accountCode;
    private String accountCharacter;
    private String accountName;
    private String accountUseCheck;
    private String accountDescription;
    private String editable;
    private String lev;
    private String budget;
}
