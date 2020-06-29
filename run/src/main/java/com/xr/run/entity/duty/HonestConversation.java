package com.xr.run.entity.duty;

import com.xr.run.entity.SysStaff;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class HonestConversation {
    private Integer id;
    private List<SysStaff> users=new ArrayList<>();
    private String type;//谈话类型
    private String time;//谈话时间
    private String site;//谈话地点
    private Integer personid;//记录人
    private String syllabus;//谈话提纲
    private String content;//谈话内容
    private String createtime;
    private String createname;
    private Integer createid;
    private Integer staus;
    private String personname;
    private Integer punit;//单位id
    private String pduty;//职务
    private Integer pid;//谈话对象
    private String politcs;//谈话对象政治面貌
}
