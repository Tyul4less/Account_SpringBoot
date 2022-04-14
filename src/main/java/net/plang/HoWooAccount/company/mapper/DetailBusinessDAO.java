package net.plang.HoWooAccount.company.mapper;

import java.util.ArrayList;

import net.plang.HoWooAccount.company.to.DetailBusinessBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetailBusinessDAO {

	public ArrayList<DetailBusinessBean> selectDetailBusinessList(String businessCode);

}
