package com.xr.run.entity.duty;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class HonestConversation {
    private Integer id;
    private Integer pid;
    private String type;
    private String time;
    private String site;
    private Integer personid;
    private String syllabus;
    private String content;
    private String createtime;
    private Integer createid;
    private Integer staus;

}
