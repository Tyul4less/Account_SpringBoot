package net.plang.HoWooAccount.system.base.applicationService;

import net.plang.HoWooAccount.system.base.mapper.CodeDAO;
import net.plang.HoWooAccount.system.base.mapper.DetailCodeDAO;
import net.plang.HoWooAccount.system.base.to.CodeBean;
import net.plang.HoWooAccount.system.base.to.DetailCodeBean;
import net.plang.HoWooAccount.system.common.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class CodeListApplicationServiceImpl implements CodeListApplicationService {

    @Autowired
    private CodeDAO codeDAO;
    @Autowired
    private DetailCodeDAO detailCodeDAO;


    @Override
    public ArrayList<DetailCodeBean> getDetailCodeList(HashMap<String, String> param) {

        ArrayList<DetailCodeBean> detailCodeList = null;
        try {
            detailCodeList = detailCodeDAO.selectDetailCodeList(param);
        } catch (Exception e) {
            throw e;
        }
        return detailCodeList;
    }

    @Override
    public ArrayList<CodeBean> findCodeList() {

        ArrayList<CodeBean> codeList = null;
        try {
            codeList = codeDAO.selectCodeList();

        } catch (DataAccessException e) {
            throw e;
        }
        return codeList;
    }

    @Override
    public void batchCodeProcess(ArrayList<CodeBean> codeList, ArrayList<DetailCodeBean> codeList2) {

        try {
            for (CodeBean code : codeList) {
                switch (code.getStatus()) {
                    case "insert":
                        codeDAO.insertCode(code);
                        break;
                    case "update":
                        codeDAO.updateCode(code);
                        break;
                    case "normal":
                        break;
                    case "delete":
                        codeDAO.deleteCode(code.getDivisionCodeNo());
                }
            }
            ArrayList<DetailCodeBean> DetailcodeList = codeList2;
            for (DetailCodeBean codeDetailBean : DetailcodeList) {
                switch (codeDetailBean.getStatus()) {
                    case "insert":
                        detailCodeDAO.insertDetailCode(codeDetailBean);
                        break;
                    case "update":
                        detailCodeDAO.updateDetailCode(codeDetailBean);
                        break;
                    case "delete":
                        detailCodeDAO.deleteDetailCode(codeDetailBean.getDetailCode());
                }
            }


        } catch (DataAccessException e) {
            throw e;
        }
    }
}
