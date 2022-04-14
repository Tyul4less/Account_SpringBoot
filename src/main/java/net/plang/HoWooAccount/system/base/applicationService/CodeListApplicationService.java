package net.plang.HoWooAccount.system.base.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import net.plang.HoWooAccount.system.base.to.CodeBean;
import net.plang.HoWooAccount.system.base.to.DetailCodeBean;

public interface CodeListApplicationService {

    public ArrayList<DetailCodeBean> getDetailCodeList(HashMap<String, String> param);

    public ArrayList<CodeBean> findCodeList();

    public void batchCodeProcess(ArrayList<CodeBean> codeList, ArrayList<DetailCodeBean> codeList2);


}
