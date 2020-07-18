import Vue from 'vue'
import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN'
import App from './App'
import router from './router'
import store from './store'
import '@/icons' // icon
import '@/permission' // 权限
import { default as api } from './utils/api'
import { hasPermission } from "./utils/hasPermission";
import  VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import moment from 'moment'
Vue.filter('comverTime', function(data, format) {
    return moment(data).format(format);
});
Vue.use(VueQuillEditor)
Vue.use(ElementUI, { locale })
Vue.prototype.api = api
    //全局的常量
Vue.prototype.hasPerm = hasPermission
Vue.prototype.mom = moment
    //全局的常量
Vue.prototype.virtualIp = 'http://localhost:4031/hoonest/tmp' /* http://localhost:4031/hoonest/tmp */
Vue.prototype.virtualdutyIp = 'http://localhost:4031/hoonest/duty'
Vue.prototype.virtualimgIp = 'http://localhost:4031/hoonest/img'
Vue.prototype.videoIp = 'http://localhost:4031/hoonest/vido'
Vue.prototype.uploadimage = 'http://localhost:8080/upload'
    //生产环境时自动设置为 false 以阻止 vue 在启动时生成生产提示。
Vue.config.productionTip = (process.env.NODE_ENV != 'production')
new Vue({
    el: '#app',
    router,
    store,
    template: '<App/>',
    components: { App }
})