package net.plang.HoWooAccount.system.authority.to;

import net.plang.HoWooAccount.system.base.to.BaseBean;

public class AuthorityEmpBean extends BaseBean {
    private String empCode;
    private String empName;
    private String authorityCode;
    private String authorityName;
    private String isAuthority;
    private String menuCode;
    private String menuName;
    private String authority;
    private String deptCode;
    
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getAuthorityCode() {
		return authorityCode;
	}
	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}
	public String getAuthorityName() {
		return authorityName;
	}
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	public String getIsAuthority() {
		return isAuthority;
	}
	public void setIsAuthority(String isAuthority) {
		this.isAuthority = isAuthority;
	}
    
}
