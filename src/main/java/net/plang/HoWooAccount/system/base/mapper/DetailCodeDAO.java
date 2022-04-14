package net.plang.HoWooAccount.system.base.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import net.plang.HoWooAccount.system.base.to.DetailCodeBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetailCodeDAO {

    ArrayList<DetailCodeBean> selectDetailCodeList(HashMap<String, String> param);

    void insertDetailCode(DetailCodeBean codeDetailBean);

    void updateDetailCode(DetailCodeBean codeDetailBean);

    void deleteDetailCode(String codeNo);

}
