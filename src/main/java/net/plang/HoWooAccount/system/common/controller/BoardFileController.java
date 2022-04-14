package net.plang.HoWooAccount.system.common.controller;

import net.plang.HoWooAccount.system.base.serviceFacade.BaseServiceFacade;
import net.plang.HoWooAccount.system.base.to.BoardBean;
import net.plang.HoWooAccount.system.common.util.BoardFileManager;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/common")
public class BoardFileController {

    @Autowired
    BaseServiceFacade baseServiceFacade;

    @PostMapping("/boardFileUpload")
    public ArrayList<BoardBean> imgFileupload(HttpServletRequest request, @RequestParam("article_file") List<MultipartFile> file) {
        System.out.println("파일 업로드 진입 : "+file);
        ArrayList<BoardBean> array = new ArrayList<>();

        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (!isMultipart) {
                return null;
            }
            System.out.println("츄라이");
            array = BoardFileManager.doFileUpload(file);//파일업로드

        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }
}