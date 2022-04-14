package net.plang.HoWooAccount.system.base.mapper;

import java.util.ArrayList;

import net.plang.HoWooAccount.system.base.to.CodeBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CodeDAO {

    public ArrayList<CodeBean> selectCodeList();

    public void insertCode(CodeBean codeBean);

    public void updateCode(CodeBean codeBean);

    public void deleteCode(String Code);

}
