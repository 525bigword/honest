import request from '@/utils/api'
export function list(data){
  return request({
    url: '/duty/deploy/list',
    method: 'post',
    data
  })
}
/*新增工作计划*/
export function add(data){
  return request({
    url: '/duty/deploy/addDeployment',
    method: 'post',
    data
  })
}
export function del(data){
  return request({
    url: '/duty/deploy/delDeployment',
    method: 'post',
    data
  })
}
export function findbytitle(data) {
  return request({
    url: '/duty/deploy/findbytitle',
    method:'post',
    data
  })
}
/*更新计划内容*/
export function updatecontent(data) {
  return request({
    url: '/duty/deploy/updateDeployment',
    method:'post',
    data
  })
}
/*提交审核*/
export function subaudit(data) {
  return request({
    url: '/duty/deploy/subauditDeployment',
    method:'post',
    data
  })
}
/*通过审核*/
export function passaudit(data) {
  return request({
    url: '/duty/deploy/passauditDeployment',
    method:'post',
    data
  })
}

