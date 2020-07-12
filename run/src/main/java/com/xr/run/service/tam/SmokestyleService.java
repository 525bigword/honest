package com.xr.run.service.tam;

import com.xr.run.entity.tam.Smokestyle;

import java.util.List;

public interface SmokestyleService {
    /*查询潭烟风貌*/
    public List<Smokestyle> findAllSmokestyle();

    /*新增*/
    public void addSmokestyle(Smokestyle smokestyle);

    /*根据标题查*/
    public List<Smokestyle> findSmokestyle(Smokestyle title);

    /*删除*/
    public void delSmokestyle(Integer smoid);

    /*更新*/
    public void gxSmokestyle(Smokestyle smokestyle);
}
