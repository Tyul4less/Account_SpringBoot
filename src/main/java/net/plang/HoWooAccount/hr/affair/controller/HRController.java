package net.plang.HoWooAccount.hr.affair.controller;

import com.google.gson.Gson;
import net.plang.HoWooAccount.hr.affair.serviceFacade.HRServiceFacade;
import net.plang.HoWooAccount.hr.affair.to.DepartmentBean;
import net.plang.HoWooAccount.hr.affair.to.EmployeeBean;
import net.plang.HoWooAccount.system.common.exception.DataAccessException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/hr")
public class HRController {

    @Autowired
    private HRServiceFacade hrServiceFacade;

    @GetMapping("/findEmployeeList")
    public ArrayList<EmployeeBean> findEmployeeList(@RequestParam String deptCode) {
        return hrServiceFacade.findEmployeeList(deptCode);
    }

    @GetMapping("/findEmployeeList1")
    public ArrayList<EmployeeBean> findEmployeeList1(HttpServletRequest request, HttpServletResponse response) {
        return hrServiceFacade.findAllEmployeeList();
    }

    @PostMapping("/findEmployee")
    public EmployeeBean findEmployee(@RequestParam(value="empCode") String empCode) {
        return hrServiceFacade.findEmployee(empCode);
    }

    @PostMapping("/batchEmpInfo")
    public EmployeeBean batchEmpInfo(@RequestParam String sendData) {
        System.out.println("sendData = " + sendData);
        Gson gson = new Gson();
        JSONObject jsonObject = JSONObject.fromObject(sendData);
        EmployeeBean employeeBean = gson.fromJson(jsonObject.toString(),EmployeeBean.class);
        hrServiceFacade.batchEmployeeInfo(employeeBean);
        return employeeBean;
    }

    @PostMapping("/batchEmp")
    public void batchEmp(@RequestParam String JoinEmployee) {
        JSONObject jsonObject = JSONObject.fromObject(JoinEmployee);
        EmployeeBean employeeBean = (EmployeeBean) JSONObject.toBean(jsonObject, EmployeeBean.class);
        hrServiceFacade.registerEmployee(employeeBean);
    }

    @GetMapping("/deleteEmployee")
    public void deleteEmployee(@ModelAttribute EmployeeBean employeeBean) {
        hrServiceFacade.removeEmployee(employeeBean);
    }

    @GetMapping("/findDeptList")
    public ArrayList<DepartmentBean> findDeptList() {
        return hrServiceFacade.findDeptList();
    }

    @GetMapping("/findDetailDeptList")
    public ArrayList<DepartmentBean> findDetailDeptList(@RequestParam String workplaceCode) {
        return hrServiceFacade.findDetailDeptList(workplaceCode);
    }

}
