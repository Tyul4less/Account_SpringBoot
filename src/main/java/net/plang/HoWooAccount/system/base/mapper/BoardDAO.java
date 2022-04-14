package net.plang.HoWooAccount.system.base.mapper;

import net.plang.HoWooAccount.system.base.to.BoardBean;
import net.plang.HoWooAccount.system.base.to.CodeBean;
import net.plang.HoWooAccount.system.base.to.MenuBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface BoardDAO {

    ArrayList<BoardBean> selectBoardList();

    ArrayList<BoardBean> selectPost(String boardNum);

    ArrayList<HashMap<String, Object>> selectBoardFileList(String boardNum);

    void insertBoard(BoardBean bean);

    void insertBoardFileData(BoardBean bean);

    void updateBoard(BoardBean bean);

}
