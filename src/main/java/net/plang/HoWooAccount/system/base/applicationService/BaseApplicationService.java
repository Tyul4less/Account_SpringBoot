package net.plang.HoWooAccount.system.base.applicationService;

import java.util.ArrayList;

import net.plang.HoWooAccount.hr.affair.to.EmployeeBean;
import net.plang.HoWooAccount.system.base.exception.DeptCodeNotFoundException;
import net.plang.HoWooAccount.system.base.exception.IdNotFoundException;
import net.plang.HoWooAccount.system.base.exception.PwMissmatchException;
import net.plang.HoWooAccount.system.base.to.IreportBean;
import net.plang.HoWooAccount.system.base.to.MenuBean;
import net.plang.HoWooAccount.system.common.exception.DataAccessException;

public interface BaseApplicationService {

    public EmployeeBean getLoginData(String empCode, String userPw) throws IdNotFoundException, DeptCodeNotFoundException, PwMissmatchException, DataAccessException;

    public ArrayList<MenuBean> findMenuNameList(String deptCode);

    public ArrayList<IreportBean> getIreportData(String slipNo);
    
    public String getPeriodNo(String today);
    
    public void insertPeriodNo(String sdate, String edate);
    
    public void setEarlyStatements(String periodNo);
}