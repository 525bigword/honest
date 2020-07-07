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
                path: 'ppost',
                name: '岗位管理',
                component: _import('system/ppost/ppost'),
                meta: { title: '岗位管理', icon: 'user' },
                menu: 'ppost'
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
            {
              path: 'post',
              name: '角色管理',
              component: _import('system/post/post'),
              meta: { title: '角色管理', icon: 'user' },
              menu: 'post'
          }, {
                path: 'log',
                name: '日志管理',
                component: _import('system/log/log'),
                meta: { title: '日志管理', icon: 'password' },
                menu: 'log'
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
            menu: 'aa'
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
                menu: 'workplan'
            }, {
                path: 'deploy',
                name: '工作部署',
                component: _import('duty/deploy'),
                meta: { title: '工作部署', icon: 'user' },
                menu: 'workdeployment'
            }, {
                path: 'talk',
                name: '廉政谈话',
                component: _import('duty/talk'),
                meta: { title: '廉政谈话', icon: 'user' },
                menu: 'honestconversation'
            }, {
                path: 'main',
                name: '主体责任',
                component: _import('duty/main'),
                meta: { title: '主体责任', icon: 'user' },
                menu: 'entityresponsibility'
            }

    ]
  },
  {
    path: '/risk',
    component: Layout,
    redirect: '/risk/',
    name: '风险防控',
    meta: {title: '风险防控', icon: 'table'},
    children: [{
      path: 'postRiskCombing',
      name: '岗位风险梳理',
      component: _import('risk/postRiskCombing'),
      meta: {title: '岗位风险梳理', icon: 'user'},
      menu: 'postRiskCombing'
    }, {
      path: 'processRick',
      name: '流程风险梳理',
      component: _import('risk/processRick'),
      meta: {title: '流程风险梳理', icon: 'user'},
      menu: 'processRick'
    }, {
      path: 'riskPointAssessment',
      name: '风险指数评定',
      component: _import('risk/riskpointassessment'),
      meta: {title: '风险指数评定', icon: 'user'},
      menu: 'riskpointassessment'
    }
    ]
  }, {
    path: '/evaluate',
    component: Layout,
    redirect: '/evaluate/',
    name: '风险评价',
    meta: {title: '风险评价', icon: 'table'},
    children: [{
      path: 'riskEvaluate',
      name: '风险占比分析',
      component: _import('evaluate/riskEvaluate'),
      meta: {title: '风险占比分析', icon: 'user'},
      menu: 'riskEvaluate'
    }]
  },
  {
    path: '/responsibility',
    component: Layout,
    redirect: '/responsibility/',
    name: '监督责任',
    meta: {title: '监督责任', icon: 'table'},
    children: [{
      path: '',
      name: '监督责任',
      component: _import('responsibility/spvduty'),
      meta: {title: '监督责任', icon: 'user'},
      menu: 'spvduty'
    }]
  }, {
    path: '/inspect',
    component: Layout,
    redirect: '/user/',
    name: '纪检报表',
    meta: {title: '纪检报表', icon: 'table'},
    children: [{
      path: '',
      name: '纪检报表',
      component: _import('inspect/report'),
      meta: {title: '纪检报表', icon: 'user'},
      menu: 'report'
    }]
  }, {
    path: '/smokestyle',
    component: Layout,
    redirect: '/user/',
    name: '潭烟风貌',
    meta: {title: '潭烟风貌', icon: 'table'},
    children: [{
      path: '',
      name: '潭烟风貌',
      component: _import('smokestyle/smokestyle'),
      meta: {title: '潭烟风貌', icon: 'user'},
      menu: 'smokestyle'
    }]
  }, {
    path: '/culture',
    component: Layout,
    redirect: '/culture/',
    name: '廉政文化',
    meta: {title: '廉政文化', icon: 'table'},
    children: [{
      path: '',
      name: '资料锦集',
      component: _import('cultrue/datakamset'),
      meta: {title: '资料锦集', icon: 'user'},
      menu: 'dataCollection'
    }, {
      path: 'windculture',
      name: '清风文苑',
      component: _import('cultrue/windculture'),
      meta: {title: '清风文苑', icon: 'user'},
      menu: 'wind'
    }]
  },
  {
    path: '/riskmanagement',
    component: Layout,
    redirect: '/riskmanagement/',
    name: '动态管理',
    meta: {title: '动态管理', icon: 'table'},
    children: [{
      path: '',
      name: '风险点预警管理',
      component: _import('riskmanagement/riskwarning'),
      meta: {title: '风险点预警管理', icon: 'user'},
      menu: 'riskpointwarning'
    }, {
      path: 'systemprocess',
      name: '制度流程完善',
      component: _import('riskmanagement/systemprocess'),
      meta: {title: '制度流程完善', icon: 'user'},
      menu: 'systemprocess'
    }]
  }, {
    path: '/daily',
    component: Layout,
    redirect: '/user/',
    name: '日常管理',
    meta: { title: '日常管理', icon: 'table' },
    children: [{
      path: 'letter',
      name: '信访举报管理',
      component: _import('daily/letter'),
      meta: { title: '信访举报管理', icon: 'user' },
      menu: 'letter'
    }, {
      path: 'supervise',
      name: '三重一大事项监管',
      component: _import('daily/supervise'),
      meta: { title: '三重一大事项监管', icon: 'user' },
      menu: 'supervise'
    }, {
      path: 'collect',
      name: '三重一大事项汇总',
      component: _import('daily/collect'),
      meta: { title: '三重一大事项汇总', icon: 'user' },
      menu: 'collect'
    }, {
      path: 'reference',
      name: '三重一大事项备案审批',
      component: _import('daily/reference'),
      meta: { title: '三重一大事项备案审批', icon: 'user' },
      menu: 'reference'
    },{
      path: 'userreport',
      name: '用户举报',
      component: _import('daily/userReport'),
      meta: { title: '用户举报', icon: 'user' },
      menu: 'userreport'
    }]
  },
    {
        path: '/workorders',
        component: Layout,
        redirect: '/workorders/',
        name: '工单',
        hidden: true,
        children: [{
            path: '',
            name: '工单',
            component: _import('workorders/workorder'),
            menu: 'spvback'
        }]
    },{
      path: '/echartInfo',
      component: Layout,
      redirect: '/echartInfo/',
      name: '风险图表',
      hidden: false,
      children: [{
          path: '',
          name: '风险图表',
          component: _import('evaluate/echatInfo'),
          menu: 'riskpointwarning'
      }]
  }, { path: '*', redirect: '/404', hidden: true }
]
