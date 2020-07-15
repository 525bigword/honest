import request from '@/utils/api'
export function list(data){
  return request({
    url: '/duty/plan/list',
    method: 'post',
    data
  })
}
/*新增工作计划*/
export function add(data){
  return request({
    url: '/duty/plan/addWorkPlan',
    method: 'post',
    data
  })
}
export function del(data){
  return request({
    url: '/duty/plan/delWorkplanByid',
    method: 'post',
    data
  })
}
export function findbytitle(data) {
  return request({
    url: '/duty/plan/findbytitle',
    method:'post',
    data
  })
}
/*更新计划内容*/
export function updatecontent(data) {
  return request({
    url: '/duty/plan/updatecontent',
    method:'post',
    data
  })
}
/*提交审核*/
export function subaudit(data) {
  return request({
    url: '/duty/plan/subaudit',
    method:'post',
    data
  })
}
/*通过审核*/
export function passaudit(data) {
  return request({
    url: '/duty/plan/passaudit',
    method:'post',
    data
  })
}
