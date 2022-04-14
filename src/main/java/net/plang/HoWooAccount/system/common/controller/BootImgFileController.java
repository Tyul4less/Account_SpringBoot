package net.plang.HoWooAccount.system.common.controller;

import net.plang.HoWooAccount.system.common.util.BootFileUploader;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BootImgFileController {

    @PostMapping("/common/imgFileupload")
    public Map<String,Object> imgFileupload(HttpServletRequest request, @RequestParam MultipartFile[] image) {
        System.out.println("파일 업로드 진입");
        Map<String,Object> map = new HashMap<>();
        String imgUrl = null;;

        HttpSession session = request.getSession();

        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (!isMultipart) {
                map.put("errorCode", -1);
                map.put("errorMsg", "정상적인 경로로 접근해주세요.");

                return null;
            }

            String empCode = (String) session.getAttribute("empCode");
            MultipartFile profileImg = null;
            System.out.println("for문 진입전");
            for (MultipartFile fileItem : image) { //확장for문으로 풀어서 파일을 꺼냄
                System.out.println("fileItem = " + fileItem.getName());
                System.out.println("fileItem = " + fileItem.getOriginalFilename());
                if ((fileItem.getName().equals("image"))) {
                    profileImg = fileItem;
                }
            }
            System.out.println("empCode = " + empCode);
            if(profileImg != null) {
                imgUrl = BootFileUploader.doFileUpload(profileImg, empCode);//파일업로드
                System.out.println("imgUrl : "+imgUrl);
            }
            /////업데이트
            session.setAttribute("empProfileImg", imgUrl);
            map.put("url", imgUrl);
            map.put("errorCode", 0);
            map.put("errorMsg", "저장성공");

        } catch (IOException e) {
            map.put("errorCode", -1);
            map.put("errorMsg", e.getMessage());
        } catch (Exception e) {
            map.put("errorCode", -2);
            map.put("errorMsg", e.getMessage());
        }

        return map;
    }
}