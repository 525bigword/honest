import api from '@/utils/api'
export function list(data){
  return api({
    url: '/duty/talk/list',
    method: 'post',
    data
  })
}
/*新增工作计划*/
export function add(data){
  return api({
    url: '/duty/talk/addHonestConversation',
    method: 'post',
    data
  })
}
export function del(data){
  return api({
    url: '/duty/talk/delhonestConversation',
    method: 'post',
    data
  })
}
export function findbytitle(data) {
  return api({
    url: '/duty/talk/findbytitle',
    method:'post',
    data
  })
}
/*更新计划内容*/
export function updatecontent(data) {
  return api({
    url: '/duty/talk/updateHonestConversation',
    method:'post',
    data
  })
}
/*提交审核*/
export function subaudit(data) {
  return api({
    url: '/duty/talk/subaudit',
    method:'post',
    data
  })
}
/*通过审核*/
export function passaudit(data) {
  return api({
    url: '/duty/talk/passaudit',
    method:'post',
    data
  })
}

