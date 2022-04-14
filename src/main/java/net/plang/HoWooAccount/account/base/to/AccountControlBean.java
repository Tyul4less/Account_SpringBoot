package net.plang.HoWooAccount.account.base.to;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.plang.HoWooAccount.system.base.to.BaseBean;

@Setter
@Getter
public class AccountControlBean extends BaseBean {
    private String accountControlCode;
    private String accountControlName;
    private String accountControlType;
    private String accountControlDescription;
}
