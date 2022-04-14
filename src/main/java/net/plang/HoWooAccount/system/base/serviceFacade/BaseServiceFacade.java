package net.plang.HoWooAccount.system.base.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.plang.HoWooAccount.hr.affair.to.EmployeeBean;
import net.plang.HoWooAccount.system.authority.to.AuthorityEmpBean;
import net.plang.HoWooAccount.system.base.exception.DeptCodeNotFoundException;
import net.plang.HoWooAccount.system.base.exception.IdNotFoundException;
import net.plang.HoWooAccount.system.base.exception.PwMissmatchException;
import net.plang.HoWooAccount.system.base.to.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface BaseServiceFacade {

    public EmployeeBean getLoginData(String empCode, String userPw) throws IdNotFoundException, PwMissmatchException, DeptCodeNotFoundException;

    public ArrayList<MenuBean> findUserMenuList(String deptCode);

    public ArrayList<DetailCodeBean> getDetailCodeList(HashMap<String, String> param);

    public ArrayList<CodeBean> findCodeList();

    public void getIreportData(HttpServletRequest request, HttpServletResponse response, String slipNo);

    public void batchCodeProcess(ArrayList<CodeBean> codeList, ArrayList<DetailCodeBean> codeList2);

	public String getPeriodNo(String today);

	public void insertPeriodNo(String sdate, String edate);

	public void setEarlyStatements(String periodNo);
	
	public ArrayList<AuthorityEmpBean> getAuthority(String empCode);

	//Board
    ArrayList<BoardBean> findBoardList();

    HashMap<String, Object> findPost(String boardNum);

    void registBoard(BoardBean bean);

    void boardFileUpload(ArrayList<BoardBean> arrayList);

    void updateBoard(BoardBean bean);

}
