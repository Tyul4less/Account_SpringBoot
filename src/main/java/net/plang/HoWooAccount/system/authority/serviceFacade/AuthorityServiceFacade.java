package net.plang.HoWooAccount.system.authority.serviceFacade;

import java.util.ArrayList;

import net.plang.HoWooAccount.system.authority.to.AuthorityEmpBean;

public interface AuthorityServiceFacade {

    public ArrayList<AuthorityEmpBean> findAuthorityEmp(String deptCode);
    public void updateAuthority(ArrayList<AuthorityEmpBean> authorityEmpBean, String dept);
}
