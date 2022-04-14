package net.plang.HoWooAccount.system.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class BootFileUploader {



    public static String doFileUpload(MultipartFile fileItem, String empId) throws IOException {
        System.out.println("파일업로드 시도 = "+fileItem + empId);
        String realFileName = fileItem.getOriginalFilename().substring(fileItem.getOriginalFilename().lastIndexOf("//") + 1);

        String fileExt = realFileName.substring(realFileName.lastIndexOf("."));

        String saveFileName = empId + fileExt;

        //어디 저장할지
        String uploadPath1 = "D:\\dev\\InteliJWorkSpace\\Account4th\\Account_SpringBoot\\src\\main\\webapp\\photos\\"+saveFileName; //이클립스
        String uploadPath2 = "C:\\Apache2\\htdocs\\photos\\"+saveFileName; //아파치
        System.out.println("uploadPath = " + uploadPath1);

        FileOutputStream fos1 = null;

        try {
            //MultipatrFile클래스의 getBytes()를 사용해서 multipartFile의 데이터를 바이트배열로 추출
            byte fileData[] = fileItem.getBytes();
            fos1 = new FileOutputStream(uploadPath1);

            //FileOutputStream클래스의 write()로 파일을 filePath에 저장
            fos1.write(fileData);

            fos1 = new FileOutputStream(uploadPath2);
            fos1.write(fileData);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fos1 != null) {
                fos1.close();
            }
        }

        return "/photos/"+saveFileName;

    }
}