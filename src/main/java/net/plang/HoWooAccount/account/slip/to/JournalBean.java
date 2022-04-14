package net.plang.HoWooAccount.account.slip.to;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.plang.HoWooAccount.system.base.to.BaseBean;

@Getter
@Setter
public class JournalBean extends BaseBean {
    private String id;
    private String slipNo;
    private String journalNo;
    private String balanceDivision;
    private String accountCode;
    private String accountName;
    private String customerCode;
    private String customerName;
    private String leftDebtorPrice;
    private String rightCreditsPrice;
    private String price;
    private String deptCode;
    private String accountPeriodNo;
    private List<JournalDetailBean> journalDetailList;

}
