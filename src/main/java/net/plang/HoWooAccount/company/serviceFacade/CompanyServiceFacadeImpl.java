package net.plang.HoWooAccount.company.serviceFacade;

import java.util.ArrayList;

import lombok.Setter;

import net.plang.HoWooAccount.company.applicationService.BusinessApplicationService;
import net.plang.HoWooAccount.company.applicationService.WorkplaceApplicationService;
import net.plang.HoWooAccount.company.to.BusinessBean;
import net.plang.HoWooAccount.company.to.DetailBusinessBean;
import net.plang.HoWooAccount.company.to.WorkplaceBean;
import net.plang.HoWooAccount.system.common.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
public class CompanyServiceFacadeImpl implements CompanyServiceFacade {

	@Autowired
	private WorkplaceApplicationService workplaceApplicationService;
	@Autowired
	private BusinessApplicationService businessApplicationService;

	@Override
	public void workplaceAdd(WorkplaceBean workplaceBean) {
		WorkplaceBean workplaceCodeCheck = workplaceApplicationService.getWorkplace(workplaceBean.getWorkplaceCode());
		if(workplaceCodeCheck==null) {
			workplaceApplicationService.workPlaceAdd(workplaceBean);
		}
	}

	@Override
	public void eliminationWorkplace(ArrayList<String> getCodes) {
		workplaceApplicationService.eliminationWorkplace(getCodes);
	}

	@Override
	public void updateApprovalStatus(ArrayList<String> getCodes,String status) {
		workplaceApplicationService.updateApprovalStatus(getCodes,status);
	}

	@Override
	public WorkplaceBean getWorkplace(String workplaceCode) {
		return workplaceApplicationService.getWorkplace(workplaceCode);
	}


	@Override
	public ArrayList<WorkplaceBean> getAllWorkplaceList () {
		return workplaceApplicationService.getAllWorkplaceList();
	}

	@Override
	public ArrayList<BusinessBean> getBusinessList() {
		return businessApplicationService.getBusinessList();
	}

	@Override
	public ArrayList<DetailBusinessBean> getDetailBusiness(String businessCode) {
		return businessApplicationService.getDetailBusiness(businessCode);
	}
}
