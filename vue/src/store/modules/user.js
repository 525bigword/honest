import { getInfo, login, logout } from '@/api/login'
import { getToken, removeToken, setToken } from '@/utils/auth'
import { default as api } from '../../utils/api'
import store from '../../store'
import router from '../../router'

const user = {
    state: {
        nickname: "",
        userId: 0,
        avatar: 'https://www.gravatar.com/avatar/6560ed55e62396e40b34aac1e5041028',
        role: "",
        menus: [],
        permissions: [],
        token: "",
        mid: 0
    },
    mutations: {
        SET_USER: (state, userInfo) => {
            state.nickname = userInfo.sysStaffByUserName.name;
            state.userId = userInfo.sysStaffByUserName.sid;
            state.role = userInfo.sysStaffByUserName.postname;
            state.menus = userInfo.menuList;
            state.mid = userInfo.sysStaffByUserName.mid;
            state.permissions = userInfo.permissionList;
        },
        SET_USER_ID: (state, userInfo) => {
            state.nickname = userInfo;

        },
        RESET_USER: (state) => {
            state.nickname = "";
            state.userId = "";
            state.role = '';
            state.menus = [];
            state.permissions = [];
        }
    },
    actions: {
        // 登录
        Login({ commit, state }, loginForm) {
            return new Promise((resolve, reject) => {
                api({
                    url: "SysStaff/auth",
                    method: "post",
                    data: loginForm
                }).then(data => {
                    console.log(data)
                    if (data.result === "success") {
                        //cookie中保存前端登录状态
                        // console.log(data.data.roleName)
                        // localStorage.setItem("roleName", data.data.postname)
                        // localStorage.setItem("roleId", data.data.pid)
                        // localStorage.setItem("userId", data.data.sid)
                        // commit('SET_USER_ID', data.data.sid);
                        setToken();
                    }
                    resolve(data);
                }).catch(err => {
                    reject(err)
                })
            })
        },
        // 获取用户信息
        GetInfo({ commit, state }) {
            return new Promise((resolve, reject) => {
                console.log("getInfo")
                api({
                    url: '/SysStaff/getInfo',
                    method: 'post'
                }).then(data => {
                    console.log(data)
                        //储存用户信息
                    commit('SET_USER', data.userPermission);
                    //cookie保存登录状态,仅靠vuex保存的话,页面刷新就会丢失登录状态
                    setToken();
                    //生成路由
                    let userPermission = data.userPermission;
                    store.dispatch('GenerateRoutes', userPermission).then(() => {
                        //生成该用户的新路由json操作完毕之后,调用vue-router的动态新增路由方法,将新路由添加
                        console.log('store.getters.addRouters',store.getters.addRouters)
                        router.addRoutes(store.getters.addRouters)
                    })
                    resolve(data)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 登出
        LogOut({ commit }) {
            return new Promise((resolve) => {
                api({
                    url: "SysStaff/logout",
                    method: "post"
                }).then(data => {
                    commit('RESET_USER')
                    removeToken()
                    resolve(data);
                }).catch(() => {
                    commit('RESET_USER')
                    removeToken()
                })
            })
        },
        // 前端 登出
        FedLogOut({ commit }) {
            return new Promise(resolve => {
                commit('RESET_USER')
                removeToken()
                resolve()
            })
        }
    }
}
export default user