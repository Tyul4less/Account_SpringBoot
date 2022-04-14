package net.plang.HoWooAccount.system.authority.applicationService;

import java.util.ArrayList;

import net.plang.HoWooAccount.hr.affair.to.DepartmentBean;
import net.plang.HoWooAccount.system.authority.to.AuthorityEmpBean;

public interface AuthorityApplicationService {
    public ArrayList<AuthorityEmpBean> findAuthorityEmp(String deptCode);
    public void updateAuthority(ArrayList<AuthorityEmpBean> authorityEmpBean, String dept);
}
