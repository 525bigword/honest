import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css' // Progress 进度条样式
import { getToken } from '@/utils/auth' // 验权
const whiteList = ['/login', '/404','/userReport', ''] //白名单,不需要登录的路由
//const i=0;
router.beforeEach((to, from, next) => {
    NProgress.start()
    if (getToken()) {
        // if(i===0){
        //     store.dispatch('GetInfo').then(() => {
        //         next({...to })
        //     })
        // }
        //i++;
        //如果已经登录
        console.log("getToken()：" + getToken())
        console.log("to.path === '/login'：" + to.path === '/login');
        console.log("!store.getters.role：" + store.getters.role)
        if (to.path === '/login') {
            next({ path: '/' })
            NProgress.done() // 结束Progress
            //&&localStorage.getItem('roleName')
        }else{
            console.log(store.getters.role)
            if (store.getters.role) {
                next()
            } else {
                store.dispatch('GetInfo').then((res) => {
                    next({...to })
                },(err)=>{
                    store.dispatch('FedLogOut').then(() => {
                        location.reload() // 为了重新实例化vue-router对象 避免bug
                     })
                })
                // const { roles } =  store.dispatch('GetInfo')
                // const accessRoutes=store.dispatch('generateRoutes', roles)
                // router.addRoutes(accessRoutes)
                // next({ ...to, replace: true })
            }
        } 
    } else if (whiteList.indexOf(to.path) !== -1) {
        //如果前往的路径是白名单内的,就可以直接前往
        next()
    } else {
        //如果路径不是白名单内的,而且又没有登录,就跳转登录页面
        store.commit('RESET_USER')
        next('/login')
        NProgress.done() // 结束Progress
    }
})
router.afterEach(() => {
    NProgress.done() // 结束Progress
})
