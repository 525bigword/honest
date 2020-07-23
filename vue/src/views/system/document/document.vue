<template>
  <el-form ref="dataForm" :rules="rules" :model="form" label-width="80px" label-position="right">
    <el-row>
      <el-col style="width:45%">
        <el-form-item label="用户名" prop="name">
          <el-input placeholder="用户名" v-model="form.name"></el-input>
        </el-form-item>
      </el-col>
      <el-col style="margin-left:10px;width:45%">
        <el-form-item label="年龄" prop="age">
          <el-input onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="年龄" v-model="form.age"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col style="width:45%">
        <el-form-item label="学历" :line="true" prop="educationalBackground">
          <el-select style="width: 100%;" v-model="form.educationalBackground" placeholder="学历">
            <el-option label="初中" value="初中"></el-option>
            <el-option label="高中" value="高中"></el-option>
            <el-option label="专科" value="专科"></el-option>
            <el-option label="本科" value="本科"></el-option>
            <el-option label="本科以上" value="本科以上"></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col style="margin-left:10px;width:45%">
        <el-form-item label="政治面貌" prop="politicalAppearance">
          <el-select style="width:100%" v-model="form.politicalAppearance" placeholder="政治面貌">
            <el-option label="团员" value="团员"></el-option>
            <el-option label="党员" value="党员"></el-option>
            <el-option label="其他" value="其他"></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col style="margin-left:10px;width:45%">
        <el-form-item label="电话号码" prop="phone">
          <el-input v-model="form.phone" placeholder="电话号码"></el-input>
        </el-form-item>
      </el-col>
      <el-col style="margin-left:10px;width:45%">
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
    </el-row>
    
    <el-row>
      <el-col style="width:45%">
        <el-form-item label="登录名" prop="username">
          <el-input
            disabled="disabled"
            style="width:100%"
            v-model="form.username"
            placeholder="登录名"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col style="margin-left:10px;width:45%">
        <el-form-item label="登录密码" prop="password">
          <el-input type="password" style="width:100%" v-model="form.password" placeholder="登录密码"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-form-item style="margin-top:15px">
      <el-button style="margin-left:63%" type="primary" @click="submit()">提交</el-button>
      <el-button type="primary" @click="clen()">取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { mapGetters } from "vuex";
import store from "../../../store";
export default {
  data() {
    return {
      form: {
        sid: store.getters.userId,
        staus: "",
        name: "",
        sex: "男",
        age: "",
        educationalBackground: "",
        politicalAppearance: "",
        phone: "",
        username: "",
        password: "",
        phone: "",
        pname: "",
        pid: undefined,
        ppid: undefined,
        mid: undefined
      },
      rules: {
        name: [
          {
            required: true,
            message: "员工名不能为空",
            trigger: ["blur", "change"]
          }
        ],
        age: [
          {
            required: true,
            message: "年龄不能为空",
            trigger: ["blur"]
          }
        ],
        educationalBackground: [
          {
            required: true,
            message: "请选择学历",
            trigger: ["change"]
          }
        ],
        politicalAppearance: [
          {
            required: true,
            message: "请选择政治面貌",
            trigger: ["change"]
          }
        ],
        phone: [
          {
            required: true,
            message: "电话号码不能为空",
            trigger: ["blur", "change"]
          }
        ],
        mid: [
          {
            required: true,
            message: "请选择所属部门",
            trigger: "change"
          }
        ],
        sex: [
          {
            required: true,
            message: "请选择员工性别",
            trigger: ["change"]
          }
        ],
        username: [
          {
            required: true,
            message: "登录名不能为空",
            trigger: ["blur", "change"]
          }
        ],
        password: [
          {
            required: true,
            message: "登录密码不能为空",
            trigger: ["blur", "change"]
          }
        ],
        ppid: [
          {
            required: true,
            message: "请选择岗位",
            trigger: ["change"]
          }
        ],
        pid: [
          {
            required: true,
            message: "请选择角色",
            trigger: ["change"]
          }
        ],
        deleteList: []
      }
    };
  },
  created() {
    store.dispatch("GetInfo").then(res => {
      console.log(res.userPermission.sysStaffByUserName);
      res.userPermission.sysStaffByUserName.password = undefined;
      this.form = res.userPermission.sysStaffByUserName;
    });
  },
  computed: {
    ...mapGetters(["sidebar", "avatar", "nickname"])
  },
  methods: {
    clen(){
      this.$router.push({ path: "/" });
    },
    submit() {
      this.api({
        url: "SysStaff/setInfo",
        method: "post",
        data: this.form
      }).then(res => {
        console.log(res, "setInfo");
        this.$message({
          type:'success',
          message:'修改成功'
        });
        this.$router.push({ path: "/" });
      });
    }
  }
};
</script>

<style>
</style>
