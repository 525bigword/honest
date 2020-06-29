package com.xr.run.entity.inspect;

import com.xr.run.entity.SysStaff;
import com.xr.run.entity.SysStaff;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class Report {
    private Integer id;
    private String reportType;
    private String report;
    private String newTime;
    private List<SysStaff> eid=new ArrayList<>();
    private Integer status;
    //功能需求
    private Integer sid;
    private String url;
}
