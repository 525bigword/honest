package com.xr.run.dao.tam;

import com.xr.run.entity.tam.Smokestyle;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SmokestyleMapper {
    /*查询潭烟风貌*/
    public List<Smokestyle> findAllSmokestyle();

    /*新增*/
    public void addSmokestyle(Smokestyle smokestyle);

    /*根据标题查*/
    public List<Smokestyle> findSmokestyle(String title);

    /*删除*/
    public void delSmokestyle(Integer smoid);

    /*更新*/
    public void gxSmokestyle(Smokestyle smokestyle);
}
