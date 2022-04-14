package net.plang.HoWooAccount.system.base.applicationService;

import net.plang.HoWooAccount.hr.affair.mapper.EmployeeDAO;
import net.plang.HoWooAccount.hr.affair.to.EmployeeBean;
import net.plang.HoWooAccount.system.base.mapper.MenuDAO;
import net.plang.HoWooAccount.system.base.mapper.PeriodDAO;
import net.plang.HoWooAccount.system.base.exception.DeptCodeNotFoundException;
import net.plang.HoWooAccount.system.base.exception.IdNotFoundException;
import net.plang.HoWooAccount.system.base.exception.PwMissmatchException;
import net.plang.HoWooAccount.system.base.to.IreportBean;
import net.plang.HoWooAccount.system.base.to.MenuBean;
import net.plang.HoWooAccount.system.common.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BaseApplicationServiceImpl implements BaseApplicationService {

    @Autowired
    private MenuDAO menuDAO;
    @Autowired
    private EmployeeDAO employeeDAO;
    @Autowired
    private PeriodDAO periodDAO;


    public void setEarlyStatements(String periodNo) {

        try {
           periodDAO.setEarlyStatements(periodNo);

        } catch (DataAccessException e) {
            throw e;

        }
    }
    
    public String getPeriodNo(String today) {

        String periodNo = null;
        try {
           periodNo = periodDAO.getPeriodNo(today);

        } catch (DataAccessException e) {
            throw e;

        }
        return periodNo;
    }
    
    public void insertPeriodNo(String sdate,String edate) {

        try {
           periodDAO.insertPeriodNo(sdate,edate);

        } catch (DataAccessException e) {
            throw e;

        }
    }
    
    //아이리포트 수정중
    @Override
    public ArrayList<IreportBean> getIreportData(String slipNo) {

        ArrayList<IreportBean> reportDataList = null;
        try {
           System.out.println("아이리포트 어플리케이션");

        } catch (DataAccessException e) {
            throw e;

        }
        return reportDataList;
    }


    @Override
    public EmployeeBean getLoginData(String empCode, String userPw) throws IdNotFoundException, DeptCodeNotFoundException, PwMissmatchException, DataAccessException {

        EmployeeBean employeeBean;

        try {
            employeeBean = employeeDAO.selectEmployee(empCode);
            if (employeeBean == null) 
                throw new IdNotFoundException("존재 하지 않는 계정입니다.");
            else {
                if (!employeeBean.getUserPw().equals(userPw)) 
                    throw new PwMissmatchException("비밀번호가 틀립니다.");
                
            }
        } catch (DataAccessException e) {
            throw e;
        }
        return employeeBean;

    }

    @Override
    public ArrayList<MenuBean> findMenuNameList(String deptCode) {

        ArrayList<MenuBean> menuList = null;
        try {
            menuList = menuDAO.selectMenuNameList(deptCode);
        } catch (DataAccessException e) {
            throw e;

        }
        return menuList;
    }
}