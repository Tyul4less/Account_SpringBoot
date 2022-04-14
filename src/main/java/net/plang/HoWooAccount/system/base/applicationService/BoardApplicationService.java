package net.plang.HoWooAccount.system.base.applicationService;

import net.plang.HoWooAccount.hr.affair.to.EmployeeBean;
import net.plang.HoWooAccount.system.base.exception.DeptCodeNotFoundException;
import net.plang.HoWooAccount.system.base.exception.IdNotFoundException;
import net.plang.HoWooAccount.system.base.exception.PwMissmatchException;
import net.plang.HoWooAccount.system.base.to.BoardBean;
import net.plang.HoWooAccount.system.base.to.IreportBean;
import net.plang.HoWooAccount.system.base.to.MenuBean;
import net.plang.HoWooAccount.system.common.exception.DataAccessException;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

public interface BoardApplicationService {

    ArrayList<BoardBean> findBoardList();

    HashMap<String, Object> findPost(String boardNum);

    void registBoard(BoardBean bean);

    void boardFileUpload(ArrayList<BoardBean> arrayList);

    void updateBoard(BoardBean bean);

}