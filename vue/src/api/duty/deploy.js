import api from '@/utils/api'
export function list(data){
  return api({
    url: '/duty/deploy/list',
    method: 'post',
    data
  })
}
/*新增工作计划*/
export function add(data){
  return api({
    url: '/duty/deploy/addDeployment',
    method: 'post',
    data
  })
}
export function del(data){
  return api({
    url: '/duty/deploy/delDeployment',
    method: 'post',
    data
  })
}
export function findbytitle(data) {
  return api({
    url: '/duty/deploy/findbytitle',
    method:'post',
    data
  })
}
/*更新计划内容*/
export function updatecontent(data) {
  return api({
    url: '/duty/deploy/updatecontent',
    method:'post',
    data
  })
}
/*提交审核*/
export function subaudit(data) {
  return request({
    url: '/duty/deploy/subaudit',
    method:'post',
    data
  })
}
/*通过审核*/
export function passaudit(data) {
  return request({
    url: '/duty/deploy/passaudit',
    method:'post',
    data
  })
}

