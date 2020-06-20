import Vue from 'vue'
import Router from 'vue-router'
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading
/* layout */
import Layout from '../views/layout/Layout'

const _import = require('./_import_' + process.env.NODE_ENV)
Vue.use(Router)
export const constantRouterMap = [
    { path: '/login', component: _import('login/index'), hidden: true },
    { path: '/404', component: _import('404'), hidden: true },
    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        name: '首页',
        hidden: true,
        children: [{
            path: 'dashboard',
            component: _import('dashboard/index')
        }]
    }

]
export default new Router({
    mode: 'history', //后端支持可开
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRouterMap
})
export const asyncRouterMap = [
    // {
    //     path: '/system',
    //     component: Layout,
    //     redirect: '/system/article',
    //     name: '功能模块',
    //     meta: { title: '功能模块', icon: 'tree' },
    //     children: [{
    //         path: 'article',
    //         name: '文章',
    //         component: _import('article/article'),
    //         meta: { title: '文章', icon: 'example' },
    //         menu: 'article'
    //     }, ]
    // },
    {
        path: '/system',
        component: Layout,
        redirect: '/system/mechanism',
        name: '系统管理',
        meta: { title: '系统管理', icon: 'table' },
        children: [{
                path: 'mechanism',
                name: '部门管理',
                component: _import('system/mechanism/mechanism'),
                meta: { title: '部门管理', icon: 'user' },
                menu: 'mechanism'
            },
            {
                path: 'post',
                name: '岗位管理',
                component: _import('system/post/post'),
                meta: { title: '岗位管理', icon: 'user' },
                menu: 'post'
            },
            {
                path: 'staff',
                name: '用户列表',
                component: _import('system/user/user'),
                meta: { title: '用户列表', icon: 'user' },
                menu: 'staff'
            },
            {
                path: 'role',
                name: '权限管理',
                component: _import('system/permission/permission'),
                meta: { title: '权限管理', icon: 'password' },
                menu: 'permission'
            },
        ]
    },
    {
        path: '/politics',
        component: Layout,
        redirect: '/politics/',
        name: '廉政教育',
        meta: { title: '廉政教育', icon: 'table' },
        children: [{
            path: '',
            name: '廉政教育',
            component: _import('user/user'),
            meta: { title: '廉政教育', icon: 'user' },
            menu: 'user'
        }]
    },
    {
        path: '/duty',
        component: Layout,
        redirect: '/user/',
        name: '责任纪实',
        meta: { title: '责任纪实', icon: 'table' },
        children: [{
                path: 'plan',
                name: '工作计划',
                component: _import('duty/plan'),
                meta: { title: '工作计划', icon: 'user' },
                menu: 'user'
            }, {
                path: 'deploy',
                name: '工作部署',
                component: _import('duty/deploy'),
                meta: { title: '工作部署', icon: 'user' },
                menu: 'user'
            }, {
                path: 'talk',
                name: '廉政谈话',
                component: _import('duty/talk'),
                meta: { title: '廉政谈话', icon: 'user' },
                menu: 'user'
            }, {
                path: 'main',
                name: '主体责任',
                component: _import('duty/main'),
                meta: { title: '主体责任', icon: 'user' },
                menu: 'user'
            }

        ]
    },
    {
        path: '/risk',
        component: Layout,
        redirect: '/risk/',
        name: '风险防控',
        meta: { title: '风险预防', icon: 'table' },
        children: [{
            path: '',
            name: '风险防控',
            component: _import('user/user'),
            meta: { title: '风险防控', icon: 'user' },
            menu: 'user'
        }, {
            path: '',
            name: '日常管理',
            component: _import('user/user'),
            meta: { title: '日常管理', icon: 'user' },
            menu: 'user'
        }, {
            path: '',
            name: '动态管理',
            component: _import('user/user'),
            meta: { title: '动态管理', icon: 'user' },
            menu: 'user'
        }, {
            path: '',
            name: '风险处置',
            component: _import('user/user'),
            meta: { title: '风险处置', icon: 'user' },
            menu: 'user'
        }, {
            path: '',
            name: '风险评价',
            component: _import('user/user'),
            meta: { title: '风险评价', icon: 'user' },
            menu: 'user'
        }]
    }, {
        path: '/supervises',
        component: Layout,
        redirect: '/supervises/',
        name: '监督责任',
        meta: { title: '监督责任', icon: 'table' },
        children: [{
            path: '',
            name: '监督责任',
            component: _import('user/user'),
            meta: { title: '监督责任', icon: 'user' },
            menu: 'user'
        }]
    }, {
        path: '/report',
        component: Layout,
        redirect: '/report/',
        name: '纪检报表',
        meta: { title: '纪检报表', icon: 'table' },
        children: [{
            path: '',
            name: '纪检报表',
            component: _import('user/user'),
            meta: { title: '纪检报表', icon: 'user' },
            menu: 'user'
        }]
    }, {
        path: '/culture',
        component: Layout,
        redirect: '/culture/',
        name: '廉政文化',
        meta: { title: '廉政文化', icon: 'table' },
        children: [{
            path: '',
            name: '资料锦集',
            component: _import('cultrue/datakamset'),
            meta: { title: '资料锦集', icon: 'user' },
            menu: 'user'
        }
        ]
    },
    { path: '*', redirect: '/404', hidden: true }
]