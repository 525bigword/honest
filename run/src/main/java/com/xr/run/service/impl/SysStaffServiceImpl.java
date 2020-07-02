package com.xr.run.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.DatacollectionMapper;
import com.xr.run.dao.RdHonestConversationMapper;
import com.xr.run.dao.SysStaffMapper;
import com.xr.run.dao.WindMapper;
import com.xr.run.entity.SysStaff;
import com.xr.run.service.SysPermissionService;
import com.xr.run.service.SysStaffService;
import com.xr.run.util.CommonUtil;
import com.xr.run.util.DateUtil;
import com.xr.run.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysStaffServiceImpl extends ServiceImpl<SysStaffMapper,SysStaff> implements SysStaffService {
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private RdHonestConversationMapper rdHonestConversationMapper;
    @Autowired
    private DatacollectionMapper datacollectionMapper;
    @Autowired
    private WindMapper windMapper;
    @Override
    public IPage<SysStaff> findSysStaffAll(Page<SysStaff> page,String name, Integer staus,Integer mid) {
        IPage<SysStaff> sysStaffAll=null;
        if(mid!=null){
            sysStaffAll = baseMapper.findSysStaffAll(page,name,staus,mid);
        }else{
            sysStaffAll=baseMapper.findSysStaffAllNoMid(page,name,staus);
        }
        return sysStaffAll;
    }

    @Override
    public List<SysStaff> findSysStaffAll() {
        return baseMapper.findSysStaffAlla();
    }

    @Override
    public JSONObject authLogin(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        JSONObject info = new JSONObject();
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            currentUser.login(token);
            System.out.println(token.toString());
            System.out.println(token.getCredentials());
            System.out.println(token.getHost());
            System.out.println(token.getPrincipal());
            System.out.println(token.getUsername());
            System.out.println(token.getPassword());
            System.out.println(token.isRememberMe());
//            JSONObject userPermission = sysPermissionService.getUserPermission(token.getUsername());
//            SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);
            info.put("result", "success");
//            info.put("data", userPermission);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            info.put("result", "fail");
        }
        return CommonUtil.successJson(info);
    }

    @Override
    public SysStaff getUser(String username, String password) {
        return baseMapper.getUser(username, password);
    }
    /**
     * 查询当前登录用户的权限等信息
     */
    @Override
    public JSONObject getInfo() {
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        SysStaff userInfo = (SysStaff) session.getAttribute(Constants.SESSION_USER_INFO);
        String username = userInfo.getUsername();
        JSONObject info = new JSONObject();
        JSONObject userPermission = sysPermissionService.getUserPermission(username);
        session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);
        info.put("userPermission", userPermission);
        return CommonUtil.successJson(info);
    }

    /**
     * 退出登录
     */
    @Override
    public JSONObject logout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (Exception e) {

        }
        return CommonUtil.successJson();
    }

    @Override
    public Integer findSysStaffToCount(String[] pids) {
        for (int i = 0; i < pids.length; i++) {
            Integer count = baseMapper.findSysStaffByPidToCount(Integer.parseInt(pids[i]));
            if(count>0){
                return count;
            }
        }
        return 0;
    }

    @Override
    public  List<SysStaff>  findSysStaff() {
        return baseMapper.findSysStaff();
    }

    @Override
    public Integer addSysStaff(SysStaff sysStaff) {
        System.out.println(sysStaff.getUsername());
        Integer sysStaffCount = baseMapper.findSysStaffCount(sysStaff.getUsername());
        if(sysStaffCount>0){
            return 0;
        }
        sysStaff.setCreateTime(DateUtil.getDate());
        baseMapper.addSysStaff(sysStaff);
        return 1;
    }

    @Override
    public void upSysStaff(SysStaff sysStaff) {
        baseMapper.upSysStaff(sysStaff);
    }

    @Override
    public Integer delSysStaff(String arr) {
        String[] split = arr.split(",");
        for (String s : split) {
            SysStaff sysStaffBySid = baseMapper.findSysStaffBySid(Integer.parseInt(s));
            int sid=sysStaffBySid.getSid();
            Integer rdHonestConversationCount = rdHonestConversationMapper.findRdHonestConversationCount(sid);
            if(rdHonestConversationCount>0){
                return 2;
            }
            Integer datacollectionByStatusAndSidToCount = datacollectionMapper.findDatacollectionByStatusAndSidToCount(sid);
            if(datacollectionByStatusAndSidToCount>0){
                return 2;
            }
            Integer windByWstatusToCount = windMapper.findWindByWstatusToCount(sid);
            if(windByWstatusToCount>0) {
                return 2;
            }
        }
        for (String s : split) {
            baseMapper.upSysStaffStausBySid(Integer.parseInt(s));
        }
        return 1;
    }

    @Override
    public SysStaff findSysStaffBySid(Integer sid) {
        SysStaff sysStaffBySidDetail = baseMapper.findSysStaffBySidDetail(sid);
        return sysStaffBySidDetail;
    }

    @Override
    public List<SysStaff> findSysStaffByMid(int mid) {
        return baseMapper.findSysStaffByMid(mid);
    }

}
