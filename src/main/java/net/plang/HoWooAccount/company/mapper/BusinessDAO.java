package net.plang.HoWooAccount.company.mapper;

import java.util.ArrayList;

import net.plang.HoWooAccount.company.to.BusinessBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BusinessDAO {

    public ArrayList<BusinessBean> selectBusinessList();

}
