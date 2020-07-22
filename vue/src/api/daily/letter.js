import request from '@/utils/api'
export function xfbh(data){
  return request({
    url: '/daily/letter/xfbh',
    method: 'post',
    data
  })
}
export function list(data){
  return request({
    url: '/daily/letter/listLetter',
    method: 'post',
    data
  })
}
export function add(data){
  return request({
    url: '/daily/letter/addletter',
    method: 'post',
    data
  })
}
export function del(data){
  return request({
    url: '/daily/letter/delletter',
    method: 'post',
    data
  })
}
export function findbyName(data){
  return request({
    url: '/daily/letter/findbyName',
    method: 'post',
    data
  })
}
export function shbc(data){
  return request({
    url: '/daily/letter/shbc',
    method: 'post',
    data
  })
}
export function turndept(data){
  return request({
    url: '/daily/letter/turndept',
    method: 'post',
    data
  })
}
/*监察科自办结果*/
export function kezhangbc(data){
  return request({
    url: '/daily/letter/kezhangbc',
    method: 'post',
    data
  })
}
/*转办部门处理结果*/
export function deptbc(data){
  return request({
    url: '/daily/letter/deptbc',
    method: 'post',
    data
  })
}
/*局领导*/
export function leaderbc(data){
  return request({
    url: '/daily/letter/leaderbc',
    method: 'post',
    data
  })
}
/*纪检组长*/
export function zzbc(data){
  return request({
    url: '/daily/letter/zzbc',
    method: 'post',
    data
  })
}
/*登录举报*/
export function finddb(data){
  return request({
    url: '/daily/letter/finddb',
    method: 'post',
    data
  })
}

