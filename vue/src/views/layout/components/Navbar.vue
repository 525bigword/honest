<template>
  <el-menu class="navbar" mode="horizontal">
    <hamburger class="hamburger-container" :toggleClick="toggleSideBar" :isActive="sidebar.opened"></hamburger>
    <breadcrumb></breadcrumb>
    <el-dropdown class="avatar-container" trigger="click">
      <div class="avatar-wrapper">
        <img class="user-avatar" :src="avatar+'?imageView2/1/w/80/h/80'" />
        <span style="display:inline-block;vertical-align:middle">{{nickname}}</span>
        <i class="el-icon-caret-bottom"></i>
      </div>
      <el-dropdown-menu class="user-dropdown" slot="dropdown">
        <el-dropdown-item v-if="hasPerm('index:add')">
          <span @click="createIndex()" style="display:block;">生成首页</span>
        </el-dropdown-item>
        <router-link class="inlineBlock" to="/workorders">
          <el-dropdown-item v-if="hasPerm('back:list')">
            <span @click="workorder" style="display:block;">待办事项</span>
          </el-dropdown-item>
        </router-link>

        <router-link class="inlineBlock" to="/document">
          <el-dropdown-item divided>
            <span style="display:block;">修改个人信息</span>
          </el-dropdown-item>
        </router-link>
        <el-dropdown-item divided>
          <span @click="logout" style="display:block;">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </el-menu>
</template>

<script>
import { mapGetters } from "vuex";
import Breadcrumb from "@/components/Breadcrumb";
import Hamburger from "@/components/Hamburger";

export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  computed: {
    ...mapGetters(["sidebar", "avatar", "nickname"])
  },
  methods: {
    createIndex() {
      this.api({
        url: "SysStaff/createIndex",
        method: "get"
      }).then(res => {
        console.log(res);
      });
    },
    toggleSideBar() {
      this.$store.dispatch("ToggleSideBar");
    },
    logout() {
      this.$store.dispatch("LogOut").then(() => {
        location.reload(); // 为了重新实例化vue-router对象 避免bug
      });
    },
    workorder() {
      this.$router.push({ path: "/workorders" });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  margin-left: 30px;
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
  position: fixed;
  top: 0px;
  width: 86%;
  z-index: 1;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .screenfull {
    position: absolute;
    right: 90px;
    top: 16px;
    color: red;
  }
  .avatar-container {
    height: 50px;
    display: inline-block;
    position: absolute;
    right: 35px;
    .avatar-wrapper {
      cursor: pointer;
      margin-top: 5px;
      position: relative;
      .user-avatar {
        width: 40px;
        height: 40px;
        border-radius: 10px;
      }
      .el-icon-caret-bottom {
        position: absolute;
        right: -20px;
        top: 25px;
        font-size: 12px;
      }
    }
  }
}
</style>

