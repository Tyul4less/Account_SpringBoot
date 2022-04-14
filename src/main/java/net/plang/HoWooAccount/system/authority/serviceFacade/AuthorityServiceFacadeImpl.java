package net.plang.HoWooAccount.system.authority.serviceFacade;

import net.plang.HoWooAccount.system.authority.applicationService.AuthorityApplicationService;
import net.plang.HoWooAccount.system.authority.to.AuthorityEmpBean;
import net.plang.HoWooAccount.system.common.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthorityServiceFacadeImpl implements AuthorityServiceFacade {
    @Autowired
    private AuthorityApplicationService authorityApplicationService;

    @Override
    public ArrayList<AuthorityEmpBean> findAuthorityEmp(String deptCode) {

        ArrayList<AuthorityEmpBean> authorityEmp = null;
        authorityEmp = authorityApplicationService.findAuthorityEmp(deptCode);
        return authorityEmp;
    }

	@Override
	public void updateAuthority(ArrayList<AuthorityEmpBean> authorityEmpBean, String dept) {

        try {
            authorityApplicationService.updateAuthority(authorityEmpBean, dept);
        } catch (DataAccessException e) {
            throw e;
        }
	}
}
