package net.plang.HoWooAccount.system.base.mapper;

import java.util.ArrayList;

import net.plang.HoWooAccount.system.base.to.AddressBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressDAO {
    /*public ArrayList<AddressBean> searchAddressList(String dong);*/

    public ArrayList<AddressBean> selectRoadList(String sido, String sigunguname, String roadname);

    public ArrayList<AddressBean> selectSigunguList(String parameter);

    public ArrayList<AddressBean> selectSidoList();

    public ArrayList<AddressBean> selectPostList(String dong);
}
