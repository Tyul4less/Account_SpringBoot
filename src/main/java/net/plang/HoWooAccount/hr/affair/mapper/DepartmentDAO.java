package net.plang.HoWooAccount.hr.affair.mapper;

import java.util.ArrayList;

import net.plang.HoWooAccount.hr.affair.to.DepartmentBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentDAO {
	
	public ArrayList<DepartmentBean> selectDeptList();
	
	public ArrayList<DepartmentBean> selectDetailDeptList(String workplaceCode);

}
