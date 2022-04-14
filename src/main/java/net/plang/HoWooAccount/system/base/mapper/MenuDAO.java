package net.plang.HoWooAccount.system.base.mapper;

import net.plang.HoWooAccount.system.base.to.MenuBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface MenuDAO {
    ArrayList<MenuBean> selectMenuList(String empCode);

    ArrayList<MenuBean> selectAllMenuList();
    
	ArrayList<MenuBean> selectMenuNameList(String deptCode);

}
