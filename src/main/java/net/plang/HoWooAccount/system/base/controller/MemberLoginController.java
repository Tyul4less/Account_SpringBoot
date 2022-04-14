package net.plang.HoWooAccount.system.base.controller;

import net.plang.HoWooAccount.hr.affair.to.EmployeeBean;
import net.plang.HoWooAccount.system.base.exception.IdNotFoundException;
import net.plang.HoWooAccount.system.base.exception.PwMissmatchException;
import net.plang.HoWooAccount.system.base.serviceFacade.BaseServiceFacade;
import net.plang.HoWooAccount.system.base.to.MenuBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class MemberLoginController {
    @Autowired
    private BaseServiceFacade baseServiceFacade;

    @SuppressWarnings("unchecked")
    @RequestMapping("/login")
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {

        String viewName = null;
        String periodNo=null;
        HashMap<String, Object> model = new HashMap<>();
        try {
            HttpSession session = request.getSession();
            System.out.println("사원코드 = " + request.getParameter("empCode"));
            String empCode = request.getParameter("empCode");
            String userPw = request.getParameter("userPw");
            String today = request.getParameter("today");
            System.out.println("today = " + today);
            EmployeeBean employeeBean = baseServiceFacade.getLoginData(empCode, userPw);
            periodNo=baseServiceFacade.getPeriodNo(today);     //회계기수를 반환함. 오늘날짜가 period기수정보 테이블에 없으면 null
            if(periodNo==null) {
               String[] str=today.split("-");   // str={"2020","02","05"}
               String sdate=str[0]+"-"+"01-01";
               String edate=str[0]+"-"+"12-31";

                baseServiceFacade.insertPeriodNo(sdate,edate);	// sdate=20200101 sdate=20201231
               periodNo=baseServiceFacade.getPeriodNo(today);
               baseServiceFacade.setEarlyStatements(periodNo);
            }

            session.setAttribute("periodNo", periodNo);

            
            
            if (employeeBean != null) {

                session.setAttribute("empCode", employeeBean.getEmpCode());
                session.setAttribute("empName", employeeBean.getEmpName());
                session.setAttribute("deptCode", employeeBean.getDeptCode());
                session.setAttribute("deptName", employeeBean.getDeptName());
                session.setAttribute("empProfileImg", employeeBean.getImage());
                session.setAttribute("positionName", employeeBean.getPositionName());

                viewName = "redirect:hello.html";
            }
            
            //계정별 메뉴 권한
			/*
			 * ArrayList<AuthorityEmpBean> authorityEmp = baseServiceFacade.getAuthority(empCode); 
			 * ArrayList<String> list = new ArrayList<String>(); 
			 * for(AuthorityEmpBean obj: authorityEmp){
			 * list.add(obj.getIsAuthority()); } session.setAttribute("list", list);
			 */
            
            // 부서별 메뉴 권한
            ArrayList<MenuBean> menuList = baseServiceFacade.findUserMenuList(employeeBean.getDeptCode());
            ArrayList<String> list = new ArrayList<String>();
            for(MenuBean menu : menuList) {
            	list.add(menu.getMenuName());
            }
            session.setAttribute("menuList", list);
        } catch (IdNotFoundException e) {
            model.put("errorCode", -1);
            model.put("errorMsg", /*e.getMessage()*/ "존재하지 않는 계정입니다");
            viewName = "loginForm";

        } catch (PwMissmatchException e) {
            model.put("errorCode", -3);
            model.put("errorMsg", /*e.getMessage()*/ "비밀번호가 맞지 않습니다");
            viewName = "loginForm";

        } catch (Exception e) {
            e.printStackTrace();
            model.put("errorCode", -4);
            model.put("errorMsg", /*e.getMessage()*/ "예기치 못한 오류 발생");
            viewName = "loginForm";
        }
        ModelAndView modelAndView = new ModelAndView(viewName, model);
        return modelAndView;
    }

}