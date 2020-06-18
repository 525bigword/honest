package com.xr.run.entity.duty;

import com.xr.run.entity.SysStaff;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class EntityResponsibility {
    private Integer id;
    private String title;
    private String content;
    private String createtime;
    private List<SysStaff> users=new ArrayList<>();
    private Integer createid;
    private Integer staus;
}
