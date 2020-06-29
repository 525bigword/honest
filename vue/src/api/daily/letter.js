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
