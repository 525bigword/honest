package com.xr.run.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.*;
import com.xr.run.dao.daily.SupervisionfilingMapper;
import com.xr.run.entity.SpvDuty;
import com.xr.run.entity.SysStaff;
import com.xr.run.service.*;
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
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Service
@CrossOrigin(allowCredentials="true")
public class SysStaffServiceImpl extends ServiceImpl<SysStaffMapper,SysStaff> implements SysStaffService {
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private RdHonestConversationMapper rdHonestConversationMapper;
    @Autowired
    private DatacollectionMapper datacollectionMapper;
    @Autowired
    private WindMapper windMapper;
    @Autowired
    private RdWorkPlanMapper rdWorkPlanMapper;
    @Autowired
    private RdWorkDeploymentMapper rdWorkDeploymentMapper;
    @Autowired
    private RdEntityResponsibilityMapper rdEntityResponsibilityMapper;
    @Autowired
    private SpvDutyMapper spvDutyMapper;
    @Autowired
    private SpvBackMapper spvBackMapper;
    @Autowired
    private LetterreportMapper letterreportMapper;
    @Autowired
    private SupervisionfilingMapper supervisionfilingMapper;
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
        System.out.println(session.getId());
        SysStaff userInfo = (SysStaff) SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_USER_INFO);
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
            //查询是否含有资料锦集的业务
            if(datacollectionMapper.findDatacollectionByStatusAndSidToCount(sid)>0){
                return 2;
            }
            //查询是否含有清风文苑的业务
            if(windMapper.findWindByWstatusToCount(sid)>0) {
                return 2;
            }
            //查询是否含有廉政谈话的业务
            if(rdHonestConversationMapper.findRdHonestConversationCount(sid)>0){
                System.out.println(rdHonestConversationMapper.findRdHonestConversationCount(sid));
                return 2;
            }
            //查询是否含有工作计划的业务
            if(rdWorkPlanMapper.findrdWorkPlanByWstatusToCount(sid)>0){
                return 2;
            }
            //查询是否含有工作部署的业务
            if(rdWorkDeploymentMapper.findrdRdWorkDeploymentByWstatusToCount(sid)>0){
                return 2;
            }
            //查询是否含有主体责任的业务
            if(rdEntityResponsibilityMapper.findrdRdEntityResponsibilityByWstatusToCount(sid)>0){
                return 2;
            }
            //查询是否含有监督责任的业务
            if(spvDutyMapper.findSpvDutyByWstatusToCount(sid)>0){
                return 2;
            }
            //查询是否含有监督反馈的业务
            if(spvBackMapper.findspvBackByWstatusToCount(sid)>0){
                return 2;
            }
            //查询是否含有信访举报管理的业务
            if(letterreportMapper.findSpvBackByWstatusToCount(sid)>0){
                return 2;
            }
            //查询是否含有三重一大事项监管的业务
            if(supervisionfilingMapper.findSupervisionfilingByWstatusToCount(sid)>0){
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

    @Override
    public String findSysStaffByIdToName(Integer sid) {
        return baseMapper.findSysStaffByIdToName(sid);
    }

}
