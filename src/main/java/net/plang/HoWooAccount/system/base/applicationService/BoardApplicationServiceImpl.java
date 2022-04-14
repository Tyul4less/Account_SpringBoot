package net.plang.HoWooAccount.system.base.applicationService;

import net.plang.HoWooAccount.hr.affair.mapper.EmployeeDAO;
import net.plang.HoWooAccount.hr.affair.to.EmployeeBean;
import net.plang.HoWooAccount.system.base.exception.DeptCodeNotFoundException;
import net.plang.HoWooAccount.system.base.exception.IdNotFoundException;
import net.plang.HoWooAccount.system.base.exception.PwMissmatchException;
import net.plang.HoWooAccount.system.base.mapper.BoardDAO;
import net.plang.HoWooAccount.system.base.mapper.MenuDAO;
import net.plang.HoWooAccount.system.base.mapper.PeriodDAO;
import net.plang.HoWooAccount.system.base.to.BoardBean;
import net.plang.HoWooAccount.system.base.to.IreportBean;
import net.plang.HoWooAccount.system.base.to.MenuBean;
import net.plang.HoWooAccount.system.common.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class BoardApplicationServiceImpl implements BoardApplicationService {

    @Autowired
    BoardDAO boardDAO;

    @Override
    public ArrayList<BoardBean> findBoardList() {
        return boardDAO.selectBoardList();
    }

    @Override
    public HashMap<String, Object> findPost(@RequestParam String boardNum) {
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<BoardBean> arrayList = null;

        ArrayList<BoardBean> beanList = boardDAO.selectPost(boardNum);
        System.out.println("뭐가 문젠데");
        ArrayList<HashMap<String, Object>> fileResult = boardDAO.selectBoardFileList(boardNum);
        System.out.println("불만있으면 좀 알려주라");
        result.put("postInfo", beanList);
        result.put("fileList", fileResult);
        System.out.println("result.toString() = " + result.toString());
        return result;
    }

    @Override
    public void registBoard(BoardBean bean) {
        boardDAO.insertBoard(bean);
    }

    @Override
    public void boardFileUpload(ArrayList<BoardBean> arrayList){
        System.out.println("SqlMap진입 전");
        for(BoardBean bean : arrayList)
            boardDAO.insertBoardFileData(bean);
        System.out.println("진입 후");
    }

    @Override
    public void updateBoard(BoardBean bean) {
        boardDAO.updateBoard(bean);
    };

}