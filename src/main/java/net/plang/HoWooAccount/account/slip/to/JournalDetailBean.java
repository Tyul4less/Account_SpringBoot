package net.plang.HoWooAccount.account.slip.to;

import lombok.Getter;
import lombok.Setter;
import net.plang.HoWooAccount.system.base.to.BaseBean;

@Getter
@Setter
public class JournalDetailBean extends BaseBean {

    private String journalDetailNo;
    private String accountControlName;
    private String accountControlType;
    private String journalDescription;
    private String accountControlDescription;
    private String journalNo;
    private String accountControlCode;
	
}
