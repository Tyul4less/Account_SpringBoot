package net.plang.HoWooAccount.account.slip.to;

import lombok.Getter;
import lombok.Setter;
import net.plang.HoWooAccount.system.base.to.BaseBean;

@Getter
@Setter
public class SlipBean extends BaseBean {

    private String id;
    private String slipNo;
    private String accountPeriodNo;
    private String deptCode;
    private String deptName;
    private String slipType;
    private String expenseReport;
    private String authorizationStatus;
    private String reportingEmpCode;
    private String reportingEmpName;
    private String reportingDate;
    private String approvalEmpCode;
    private String approvalDate;
    private String slipStatus;
    private String balanceDivision;
    private String positionCode;

}
