package net.plang.HoWooAccount.company.controller;

import com.google.gson.Gson;
import lombok.Setter;
import net.plang.HoWooAccount.company.serviceFacade.CompanyServiceFacade;
import net.plang.HoWooAccount.company.to.WorkplaceBean;
import net.plang.HoWooAccount.system.common.util.BeanCreator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/company")
public class WorkPlaceController {

    @Autowired
	private CompanyServiceFacade companyServiceFacade;

    @PostMapping("/workPlaceAdd")
    public HashMap<String, Object> workPlaceAdd(@RequestParam String workplaceAddItems) {

        Gson gson = new Gson();
        JSONObject workplaceAddItemsJson = JSONObject.fromObject(workplaceAddItems);
        WorkplaceBean workplaceBean = gson.fromJson(workplaceAddItemsJson.toString(), WorkplaceBean.class);

        HashMap<String, Object> map = new HashMap<>();
        companyServiceFacade.workplaceAdd(workplaceBean); //insert



        return map;
    }

    @PostMapping("/eliminationWorkplace")
    public HashMap<String, Object> eliminationWorkplace(@RequestParam String codes) {

        HashMap<String, Object> map = new HashMap<>();
        ArrayList<String> getCodes=new ArrayList<>();

        JSONArray jsonArray=JSONArray.fromObject(codes);
        for(Object obj :jsonArray) {
            String code=(String)obj;
            getCodes.add(code);
        }

        companyServiceFacade.eliminationWorkplace(getCodes); //delete
        map.put("errorCode", 0);
        map.put("errorMsg","회사삭제완료");
        return map;
    }
    
    //사업
    @PostMapping("/getWorkplace")
    public  HashMap<String, Object> getWorkplace(@RequestParam String workplaceCode) {
        HashMap<String, Object> map = new HashMap<>();
        WorkplaceBean  workplaceBean=companyServiceFacade.getWorkplace(workplaceCode);

        map.put("workplaceBean", workplaceBean);

        return map;
    }
    
    @PostMapping("/getAllWorkplaceList")
	public ArrayList<WorkplaceBean> getAllWorkplaceList(HttpServletRequest request, HttpServletResponse response) {
		return companyServiceFacade.getAllWorkplaceList();
	}
	
    @PostMapping("/updateApprovalStatus")
	public HashMap<String, Object>  updateApprovalStatus(@RequestParam String status, @RequestParam String codes) {
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<String> getCodes=new ArrayList<>();

        JSONArray jsonArray=JSONArray.fromObject(codes);

        for(Object obj :jsonArray) {
            String code=(String)obj;
             getCodes.add(code);
        }

        companyServiceFacade.updateApprovalStatus(getCodes,status);

        map.put("errorCode",0);
        map.put("errorMsg","거래처변경완료");
        return map;
	}
}
