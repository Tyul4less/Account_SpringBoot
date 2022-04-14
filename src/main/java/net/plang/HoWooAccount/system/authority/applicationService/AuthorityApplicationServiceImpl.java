package net.plang.HoWooAccount.system.authority.applicationService;

import java.util.ArrayList;

import net.plang.HoWooAccount.system.authority.mapper.AuthorityDAO;
import net.plang.HoWooAccount.system.authority.to.AuthorityEmpBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorityApplicationServiceImpl implements AuthorityApplicationService {
	@Autowired
    private AuthorityDAO authorityDAO;

	@Override
	public ArrayList<AuthorityEmpBean> findAuthorityEmp(String deptCode) {
		ArrayList<AuthorityEmpBean> authorityEmp = authorityDAO.selectAuthorityEmp(deptCode);
		return authorityEmp;
	}

	@Override
	public void updateAuthority(ArrayList<AuthorityEmpBean> authorityEmpBean, String dept) {
		for(AuthorityEmpBean bean : authorityEmpBean) {
			authorityDAO.updateAuthority(bean, dept);
		}
	}
}
