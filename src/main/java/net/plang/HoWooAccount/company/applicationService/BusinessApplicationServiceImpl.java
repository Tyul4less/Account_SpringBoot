package net.plang.HoWooAccount.company.applicationService;

import net.plang.HoWooAccount.company.mapper.BusinessDAO;
import net.plang.HoWooAccount.company.mapper.DetailBusinessDAO;
import net.plang.HoWooAccount.company.to.BusinessBean;
import net.plang.HoWooAccount.company.to.DetailBusinessBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BusinessApplicationServiceImpl implements BusinessApplicationService{

	@Autowired
	private BusinessDAO businessDAO;
	@Autowired
	private DetailBusinessDAO detailBusinessDAO;

	@Override
	public ArrayList<BusinessBean> getBusinessList() {
		return businessDAO.selectBusinessList(); //
	}

	@Override
	public ArrayList<DetailBusinessBean> getDetailBusiness(String businessCode) {
		return detailBusinessDAO.selectDetailBusinessList(businessCode);
	}
}
