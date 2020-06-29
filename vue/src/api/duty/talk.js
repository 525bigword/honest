import request from '@/utils/api'
export function list(data){
  return request({
    url: '/duty/talk/list',
    method: 'post',
    data
  })
}
/*新增工作计划*/
export function add(data){
  return request({
    url: '/duty/talk/addHonestConversation',
    method: 'post',
    data
  })
}
export function del(data){
  return request({
    url: '/duty/talk/delhonestConversation',
    method: 'post',
    data
  })
}
export function findbytitle(data) {
  return request({
    url: '/duty/talk/findbytitle',
    method:'post',
    data
  })
}
/*更新计划内容*/
export function updatecontent(data) {
  return request({
    url: '/duty/talk/updateHonestConversation',
    method:'post',
    data
  })
}
/*提交审核*/
export function subaudit(data) {
  return request({
    url: '/duty/talk/subauditHonestConversation',
    method:'post',
    data
  })
}
/*通过审核*/
export function passaudit(data) {
  return request({
    url: '/duty/talk/passauditHonestConversation',
    method:'post',
    data
  })
}
/*级联选择框*/
export function getFileGroup(data) {
return request({
  url:'/duty/talk/getFileGroup',
  method:'post',
  data
})
}
/*select选择框*/
export function initpersons(data) {
  return request({
    url:'/duty/talk/initpersons',
    method:'post',
    data
  })
}

