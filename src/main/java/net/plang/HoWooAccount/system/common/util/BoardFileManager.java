package net.plang.HoWooAccount.system.common.util;

import net.plang.HoWooAccount.system.base.to.BoardBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/utill")
public class BoardFileManager {

    public static ArrayList<BoardBean> doFileUpload(List<MultipartFile> fileItem) throws IOException {

        System.out.println("파일업로드 시도 = "+fileItem);
        FileOutputStream fos = null;

        ArrayList<BoardBean> array = new ArrayList<>();

        try{
            for(MultipartFile Item : fileItem) {
                System.out.println("Item = " + Item);
                UUID uuid = UUID.randomUUID();
                BoardBean bean = new BoardBean();
                System.out.println("Item.getOriginalFilename() = " + Item.getOriginalFilename());

                String fileExt = Item.getOriginalFilename().substring(Item.getOriginalFilename().lastIndexOf("."));

                String saveFileName = uuid.toString() + fileExt;

                String uploadPath = "D:\\dev\\InteliJWorkSpace\\Account4th\\Account_SpringBoot\\src\\main\\webapp\\resources\\attachments\\" + saveFileName; //이클립스
                System.out.println("uploadPath = " + uploadPath);


                byte[] fileData = Item.getBytes();
                fos = new FileOutputStream(uploadPath);

                fos.write(fileData);
                System.out.println(Item.getOriginalFilename()+", "+uploadPath);
                bean.setFileOriname(Item.getOriginalFilename());
                bean.setFileUrl("/resources/attachments/"+saveFileName);
                array.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fos != null) {
                fos.close();
            }
        }

        return array;

        }

    @GetMapping (value="/fileDownload", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Object> boardFileDownload(@RequestParam String fileOriname, @RequestParam String localPath) {

        System.out.println("다운로드 진입"+fileOriname);
        System.out.println("다운로드 진입"+localPath);
        String path = "D:/dev/InteliJWorkSpace/Account4th/Account_SpringBoot/src/main/webapp"+localPath;

        try {
            Path filePath = Paths.get(path);
            Resource resource = new InputStreamResource(Files
                    .newInputStream(filePath)); // 파일 resource 얻기

            File file = new File(path);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-disposition","attachment;filename="+new String(fileOriname.getBytes(),"iso-8859-1"));
            //headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileOriname).build());  // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더

            return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
        }
    }
    }