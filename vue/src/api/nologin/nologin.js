import request from '@/utils/api'
export function getFileGroup(data){
  return request({
    url: '/nologin/getFileGroup',
    method: 'post',
    data
  })
}
export function initdept(data){
  return request({
    url: '/nologin/initdept',
    method: 'post',
    data
  })
}
export function initemploy(data){
  return request({
    url: '/nologin/initemploy',
    method: 'post',
    data
  })
}
export function add(data){
  return request({
    url: '/nologin/addletter',
    method: 'post',
    data
  })
}
export function letterreporter(){
  return request({
    url: '/nologin/letterreporter',
    method: 'post'
  })
}
