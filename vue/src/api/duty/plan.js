import api from '@/utils/api'
export function list(data){
  return api({
    url: '/duty/plan/list',
    method: 'post',
    data
  })
}
/*新增工作计划*/
export function add(data){
  return api({
    url: '/duty/plan/addWorkPlan',
    method: 'post',
    data
  })
}
export function del(data){
  return api({
    url: '/duty/plan/delWorkplanByid',
    method: 'post',
    data
  })
}
export function findbytitle(data) {
return api({
  url: '/duty/plan/findbytitle',
  method:'post',
  data
})
}
/*更新计划内容*/
export function updatecontent(data) {
  return api({
    url: '/duty/plan/updatecontent',
    method:'post',
    data
  })
}
/*提交审核*/
export function subaudit(data) {
  return api({
    url: '/duty/plan/subaudit',
    method:'post',
    data
  })
}
/*通过审核*/
export function passaudit(data) {
  return api({
    url: '/duty/plan/passaudit',
    method:'post',
    data
  })
}
