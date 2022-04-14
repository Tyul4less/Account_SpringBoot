package net.plang.HoWooAccount.system.base.to;

import lombok.Getter;
import lombok.Setter;

import java.security.PrivateKey;

@Setter
@Getter
public class BoardBean extends BaseBean {

    private String id;
    private String title;
    private String contents;
    private String writtenBy;
    private String writeDate;


    private String boardNum;
    private String fileUrl;
    private String fileOriname;
    private String updateBy;
    private String updateDate;
    private String lookUp;
    private String boardLike;

}
