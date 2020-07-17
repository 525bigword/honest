package com.xr.run.controller.risk;


import com.xr.run.entity.PostRiskCombing2;
import com.xr.run.service.risk.PostRiskCombingService;
import com.xr.run.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("postRiskCombing2")
//@Api("风险预防-岗位风险梳理相关接口")
public class PostRiskCombingController2 {
    // 依赖注入业务类
    @Autowired
    private PostRiskCombingService postRiskCombingService;

    /*@Autowired
    private RiskPointAssessmentService riskPointAssessmentService;*/


    @RequestMapping("list")
    //@RequiresPermissions("postRiskCombing:list") //访问权限
    public ResponseResult list(Integer deptId, Integer postId, String riskGrade, @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize) {

        /*//当前操作对象
        Session session = SecurityUtils.getSubject().getSession();
        SysStaff loginStaff = (SysStaff) session.getAttribute(Constants.SESSION_USER_INFO);
        // 条件对象
        PostRiskCombing2 postRiskCombing2 =new PostRiskCombing2();
        // 部门
        postRiskCombing2.setPdeptid(deptId);
        // 岗位
        postRiskCombing2.setPinfomationid(postId);
        // 风险等级
        if(riskGrade!=null) {
            if (riskGrade.equals("")) {
                riskGrade = null;
                postRiskCombing2.setPgrade(riskGrade);
            } else {
                postRiskCombing2.setPgrade(riskGrade.split(",")[0]);
            }
        }
        //引入分页查询，使用PageHelper分页功能在查询之前传入当前页，然后多少记录
        //Page page = PageHelper.startPage(pageNum, pageSize);
        //startPage后紧跟的这个查询就是分页查询
        List<PostRiskCombing2> list = postRiskCombingService.list(postRiskCombing2);
        //总数据行数
        Integer total = postRiskCombingService.list(postRiskCombing2).size();
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        //PageInfo<PostRiskCombing2> pageInfo = new PageInfo<PostRiskCombing2>(list);
        ResponseResult result = new ResponseResult();
        result.getInfo().put("items",list);
        result.getInfo().put("total", total);
        result.getInfo().put("loginStaff", loginStaff);
        //System.out.println(pageInfo.getPageSize());
        return result;*/
        return null;
    }

    @RequestMapping("findByPcID")
    //@RequiresPermissions("postRiskCombing:update") //访问权限
    public ResponseResult findByPcID(Integer pcid){
        List<PostRiskCombing2> list=postRiskCombingService.findByPcID(pcid);
        ResponseResult result = new ResponseResult();
        result.getInfo().put("items",list);
        return result;
    }

    /*@RequestMapping("add")
    //@RequiresPermissions("postRiskCombing:add") //访问权限
    public ResponseResult add(PostRiskCombing postRiskCombing){
        RiskPointAssessment riskPointAssessment=new RiskPointAssessment();
        //postRiskCombingService.add(postRiskCombing);
        // 修改风险点评定表的数据
        List<SysMechanism> smList=riskPointAssessmentService.findAllDept();
        for (SysMechanism sm : smList) {
            // 统计风险等级数量
            postRiskCombing.setPdeptid(sm.getMid());
            postRiskCombing.setPgrade("一级风险");
            Integer num1 = postRiskCombingService.findByGrade(postRiskCombing);
            postRiskCombing.setPgrade("二级风险");
            Integer num2 = postRiskCombingService.findByGrade(postRiskCombing);
            postRiskCombing.setPgrade("三级风险");
            Integer num3 = postRiskCombingService.findByGrade(postRiskCombing);
            // 风险点赋值
            riskPointAssessment.setRiskimfomation(sm.getMid());
            riskPointAssessment.setNumberonerisks(num1);
            riskPointAssessment.setNumbertworisks(num2);
            riskPointAssessment.setNumberthreerisks(num3);
            //System.out.println(riskPointAssessment);
            // 更新数据
            riskPointAssessmentService.update(riskPointAssessment);
        }
        ResponseResult result = new ResponseResult();
        result.getData().put("message","添加成功");
        return result;
    }*/

    /*@RequestMapping("update")
    //@RequiresPermissions("postRiskCombing:update") //访问权限
    public ResponseResult update(testEntity testData){
        //System.out.println(testData);
        PostRiskCombing postRiskCombing=new PostRiskCombing();
        postRiskCombing.setPcid(testData.getId());
        postRiskCombing.setPriskid(testData.getPrId());
        postRiskCombing.setPyear(testData.getPYear());
        postRiskCombing.setPdeptid(testData.getPDeptId());
        postRiskCombing.setPinfomationid(testData.getPPostId());
        postRiskCombing.setPproject(testData.getPProject());
        postRiskCombing.setPriskpointdescription(testData.getPRPD());
        postRiskCombing.setPprobablelvalue(testData.getPLV());
        postRiskCombing.setPcvalue(testData.getPCV());
        postRiskCombing.setPdvalue(testData.getPDV());
        postRiskCombing.setPgrade(testData.getPGD());
        postRiskCombing.setPmeasures(testData.getPMs());
        postRiskCombing.setPcreateid(testData.getPCI());
        postRiskCombing.setPcreatename(testData.getPCN());
        // System.out.println(postRiskCombing);
        postRiskCombingService.update(postRiskCombing);
        ResponseResult result = new ResponseResult();
        result.getData().put("message","修改成功");
        return result;
    }*/

    @RequestMapping("delete")
    //@RequiresPermissions("postRiskCombing:delete") //访问权限
    public ResponseResult deleteById(String pcid){
        List<String> resultID= Arrays.asList(pcid.split(","));
        List<Integer> list=new ArrayList<>();
        for (String s: resultID){
            list.add(Integer.parseInt(s));
        }
        /*System.out.println(pcid);
        System.out.println(resultID);
        System.out.println(list);*/
        postRiskCombingService.deleteById(list);
        ResponseResult result = new ResponseResult();
        result.getInfo().put("message","删除成功");
        return result;
    }
}
