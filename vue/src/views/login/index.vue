<template>
  <!-- style="top:0;position:fixed;width:100%;height:100%;background-image: url(http://localhost:9520/src/image/login-bg.jpg)" -->
  <div id="login" class="login">
         <el-container class="ms-admin-login-container">
            <el-main class="ms-admin-login-main">
               <div class="ms-admin-login-warp"  @keydown.13="handleLogin">
          <img src="http://localhost:9520/src/image/ms-admin/4.7.0/images/login-slogn.png" />
                  <el-form :model="loginForm"  :rules="loginRules" ref="loginForm" label-width="100px"  class="ms-admin-login-form">
                     <h1>
                        <span style="color: #529076">用户登录 / </span>User login</h1>
                     <el-form-item style="margin-top:35px" class="ms-admin-form-item" prop="username">
                        <el-input  type="text" placeholder='用户名' v-model="loginForm.username" autocomplete="off"></el-input>
                     </el-form-item>
                     <el-form-item style="margin-top:25px" class="ms-admin-form-item" prop="password">
                        <el-input type="password" placeholder='密码' v-model="loginForm.password" autocomplete="off"></el-input>
                     </el-form-item>
                     <el-form-item style="margin-top:30px" class="ms-admin-form-item">
                        <el-button type="primary" style="background-color: #529076;" :loading="loading" @click.native.prevent="handleLogin" class="ms-admin-login-btn">登录</el-button>
                     </el-form-item>
                  </el-form>
               </div>
            </el-main>
         </el-container>
      </div>
</template>
<script>
export default {
  name: "login",
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [
          { required: true, trigger:['blur','change'], message: "请输入用户名" }
        ],
        password: [{ required: true, trigger: ['blur','change'], message: "请输入密码" }]
      },
      loading: false
    };
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          // this.$router.push({path: '/'})
          this.$store
            .dispatch("Login", this.loginForm)
            .then(data => {
              this.loading = false;
              if ("success" === data.result) {
                this.$router.push({ path: "/" });
              } else {
                this.$message.error("账号/密码错误");
              }
            })
            .catch(() => {
              this.loading = false;
            });
        } else {
          return false;
        }
      });
    }
  }
};
</script>
<style rel="stylesheet/scss" lang="scss">
.ms-admin-login-form {
  padding: 49px 54px !important;
}
.el-radio__inner {
  border: 1px solid #000000;
  width: 16px;
  height: 16px;
}
.login {
    width: 100vw;
    height: 100vh;
}

 .ms-admin-login-container {
    height: 100%;
    background: url('/src/image/login-bg.jpg') center no-repeat;
    background-size: cover;
}

 .ms-admin-login-main {
    display: flex;
    justify-content: center;
    align-items: center;
}

 .ms-admin-login-warp {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 680px;
    height: 320px;
}

 .ms-admin-login-warp>img {
    flex: 1;
    display: inline-block;
}

 .ms-admin-login-form {
    flex: 1;
    height: 320px;
    background: #fff;
    padding: 20px 54px;
}

 .ms-admin-login-form h1 {
    line-height: 1;
    font-weight: initial;
    font-size: 12px;
    color: #999999;
    margin: 0 0 20px 0;
}

 .ms-admin-login-form h1 span {
    font-weight: initial;
    font-size: 16px;
    color: #0099ff;
}

 .ms-admin-form-item {
    margin-bottom: 20px;
}

 .ms-admin-form-item>div {
    margin-left: 0 !important;
}

 .ms-admin-form-item input {
    height: 34px !important;
    line-height: 34px !important;
}

 .ms-admin-login-btn {
    width: 100%;
    height: 34px;
    line-height: 5px;
}
</style>
