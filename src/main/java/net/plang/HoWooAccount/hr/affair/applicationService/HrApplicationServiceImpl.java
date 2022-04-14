package net.plang.HoWooAccount.hr.affair.applicationService;

import java.util.ArrayList;

import net.plang.HoWooAccount.hr.affair.mapper.DepartmentDAO;
import net.plang.HoWooAccount.hr.affair.mapper.EmployeeDAO;
import net.plang.HoWooAccount.hr.affair.to.DepartmentBean;
import net.plang.HoWooAccount.hr.affair.to.EmployeeBean;
import net.plang.HoWooAccount.system.base.mapper.DetailCodeDAO;
import net.plang.HoWooAccount.system.base.to.DetailCodeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HrApplicationServiceImpl implements HrApplicationService {

    @Autowired
    private EmployeeDAO employeeDAO;
    @Autowired
    private DetailCodeDAO detailCodeDAO;
    @Autowired
    private DepartmentDAO departmentDAO;


    @Override
    public ArrayList<EmployeeBean> findEmployeeList(String deptCode) {
        return employeeDAO.selectEmployeeList(deptCode);
    }

    @Override
    public EmployeeBean findEmployee(String empCode) {
        return employeeDAO.selectEmployee(empCode);
    }

    @Override
    public void batchEmployeeInfo(EmployeeBean employeeBean) {
        employeeDAO.updateEmployeeInfo(employeeBean);
    }

    private void modifyEmployee(EmployeeBean employeeBean) {
        employeeDAO.updateEmployee(employeeBean);
        String empCode = employeeBean.getEmpCode();
        String empName = employeeBean.getEmpName();
        DetailCodeBean detailCodeBean = new DetailCodeBean();
        detailCodeBean.setDivisionCodeNo("HR-02");
        detailCodeBean.setDetailCode(empCode);
        detailCodeBean.setDetailCodeName(empName);
        detailCodeDAO.updateDetailCode(detailCodeBean);
    }

    public void removeEmployee(EmployeeBean employBean) {
        employeeDAO.deleteEmployee(employBean.getEmpCode());
        detailCodeDAO.deleteDetailCode(employBean.getEmpCode());
    }

    @Override
    public void registerEmployee(EmployeeBean employeeBean) {
        employeeDAO.insertEmployee(employeeBean);
        String empCode = employeeBean.getEmpCode();
        String empName = employeeBean.getEmpName();
        DetailCodeBean detailCodeBean = new DetailCodeBean();
        detailCodeBean.setDivisionCodeNo("HR-02");
        detailCodeBean.setDetailCode(empCode);
        detailCodeBean.setDetailCodeName(empName);
        detailCodeDAO.insertDetailCode(detailCodeBean);
    }

	@Override
	public ArrayList<DepartmentBean> findDeptList() {
        return departmentDAO.selectDeptList();
	}

	@Override
	public ArrayList<DepartmentBean> findDetailDeptList(String workplaceCode) {
        return departmentDAO.selectDetailDeptList(workplaceCode);
	}

    @Override
    public ArrayList<EmployeeBean> findAllEmployeeList() {
        return employeeDAO.selectAllEmployeeList();
    }

}
