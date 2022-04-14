package net.plang.HoWooAccount.hr.affair.mapper;

import java.util.ArrayList;

import net.plang.HoWooAccount.hr.affair.to.EmployeeBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeDAO {

    public ArrayList<EmployeeBean> selectEmployeeList(String deptCode);

    public ArrayList<EmployeeBean> selectAllEmployeeList();

    public void updateEmployeeInfo(EmployeeBean employeeBean);

    public void updateEmployee(EmployeeBean employBean);

    public void deleteEmployee(String empCode);

    public void insertEmployee(EmployeeBean employeeBean);

    public EmployeeBean selectEmployee(String EmpCode);

}
