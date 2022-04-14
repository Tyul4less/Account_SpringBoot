package net.plang.HoWooAccount.company.controller;

import net.plang.HoWooAccount.company.serviceFacade.CompanyServiceFacade;
import net.plang.HoWooAccount.company.to.BusinessBean;
import net.plang.HoWooAccount.company.to.DetailBusinessBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/company")
public class BusinessController {

	@Autowired
	private CompanyServiceFacade companyServiceFacade;

	@PostMapping("/getBusinessList")
	public ArrayList<BusinessBean> getBusinessList() {
		return companyServiceFacade.getBusinessList();
	}
	
	@GetMapping("/getDetailBusiness")
	public ArrayList<DetailBusinessBean> getDetailBusiness(@RequestParam String businessCode) {
		return companyServiceFacade.getDetailBusiness(businessCode);
	}
}
