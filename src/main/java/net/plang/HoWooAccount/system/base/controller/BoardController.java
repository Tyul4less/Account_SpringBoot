package net.plang.HoWooAccount.system.base.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.plang.HoWooAccount.system.base.serviceFacade.BaseServiceFacade;
import net.plang.HoWooAccount.system.base.to.BoardBean;
import net.plang.HoWooAccount.system.base.to.MenuBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/base")
public class BoardController {

    @Autowired
    private BaseServiceFacade baseServiceFacade;

    @PostMapping("/findBoardList")
    public ArrayList<BoardBean> findBoardList() {
        return baseServiceFacade.findBoardList();
    }

    @GetMapping("/findPost")
    public HashMap<String, Object> findPost(@RequestParam String boardNum) {
        return baseServiceFacade.findPost(boardNum);
    }

    @PostMapping("/registBoard")
    public HashMap<String, String> registBoard(@RequestParam(value = "sendData") String boardData, @RequestParam(value = "fileSendData", required = false) String fileData) {
        System.out.println("boardData = " + boardData);
        ArrayList<BoardBean> arrayList = new ArrayList<>();
        Gson gson = new Gson();
        BoardBean bean = gson.fromJson(boardData, BoardBean.class);
        baseServiceFacade.registBoard(bean);
        if(fileData != null){
            System.out.println("fileData = " + fileData);
            System.out.println("게시판 파일 데이터 INSERT");
            JSONArray jsonArray = JSONArray.fromObject(fileData);
            System.out.println("jsonArray = " + jsonArray);
            for(Object file : jsonArray){
                BoardBean fileBean = new BoardBean();
                fileBean = gson.fromJson(file.toString(), BoardBean.class);
                arrayList.add(fileBean);
            }
            baseServiceFacade.boardFileUpload(arrayList);
        }
        return new HashMap<>();
    }

    //파일 업로드를 할때 최고 높은 보드 아이디로 하는데 업데이트하면서 파일을 추가한 경우 업데이트한 보드 아이디가 날라가야함, 그런데 그걸 수정하기엔 시간이 모자람
/*    @PostMapping("/updateBoard")
    public HashMap<String, String> updateBoard(
            @RequestParam(value = "sendData") String boardData,
            @RequestParam(value = "boardNum") String boardNum,
            @RequestParam(value = "fileSendData", required = false) String fileData,
            @RequestParam(value = "updateFileData", required = false) List<String> updateFileData) {
        System.out.println("boardData = " + boardData);
        ArrayList<BoardBean> arrayList = new ArrayList<>();
        Gson gson = new Gson();
        BoardBean bean = gson.fromJson(boardData, BoardBean.class);
        bean.setBoardNum(boardNum);
        baseServiceFacade.updateBoard(bean);
        if(fileData != null){
            System.out.println("fileData = " + fileData);
            System.out.println("게시판 파일 데이터 INSERT");
            JSONArray jsonArray = JSONArray.fromObject(fileData);
            System.out.println("jsonArray = " + jsonArray);
            for(Object file : jsonArray){
                BoardBean fileBean = new BoardBean();
                fileBean = gson.fromJson(file.toString(), BoardBean.class);
                arrayList.add(fileBean);
            }
            arrayList.add(Boa)
            baseServiceFacade.boardFileUpload(arrayList);
        }
        if(updateFileData != null){
            baseServiceFacade.
        }
        return new HashMap<>();
    }*/
}
