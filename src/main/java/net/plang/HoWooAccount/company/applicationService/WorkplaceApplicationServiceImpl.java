package net.plang.HoWooAccount.company.applicationService;


import java.util.ArrayList;

import net.plang.HoWooAccount.company.mapper.WorkplaceDAO;
import net.plang.HoWooAccount.company.to.WorkplaceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkplaceApplicationServiceImpl implements WorkplaceApplicationService {

    @Autowired
    private WorkplaceDAO workplaceDAO;

 
    // 사업장조회
    @Override
    public WorkplaceBean getWorkplace(String workplaceCode) {
        return workplaceDAO.selectWorkplace(workplaceCode);
    }
    
    // 승인상태 업뎃
    @Override
    public void updateApprovalStatus(ArrayList<String> getCodes,String status) {
        for(String code : getCodes)
            workplaceDAO.updateWorkplaceAccount(code, status);
    }
   
    //사업장추가
    @Override
    public void workPlaceAdd(WorkplaceBean workplaceBean) {
        workplaceDAO.insertWorkplace(workplaceBean);
    }
    
    //사업장삭제
    @Override
    public void eliminationWorkplace(ArrayList<String> getCodes) {
        for(String code : getCodes)
            workplaceDAO.deleteWorkplace(code);
    }
    
    // 사업장 승인상태 조회
    @Override
    public ArrayList<WorkplaceBean> getAllWorkplaceList() {
		return workplaceDAO.selectAllWorkplaceList();
    }
}
