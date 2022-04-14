package net.plang.HoWooAccount.hr.affair.to;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.plang.HoWooAccount.system.base.to.BaseBean;

@Setter
@Getter
public class EmployeeBean extends BaseBean {

    private String BasicAddress;
    private String DetailAddress;
    private String userOrNot;
    private String deptName;
    private String empCode;
    private String empName;
    private String companyCode;
    private String workPlaceCode;
    private String deptCode;
    private String positionCode;
    private String positionName;
    private String socialSecurityNumber;
    private String birthDate;
    private String gender;
    private String eMail;
    private String phoneNumber;
    private String image;
    private String userPw;
    private String ZipCode;

}
