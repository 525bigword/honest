import request from '@/utils/api'
export function list(data){
  return request({
    url: '/inspect/report/list',
    method: 'post',
    data
  })
}
export function findwhere(data){
  return request({
    url: '/inspect/report/findwhere',
    method: 'post',
    data
  })
}
export function addreport(data){
  return request({
    url: '/inspect/report/addreport',
    method: 'post',
    data
  })
}
export function del(data){
  return request({
    url: '/inspect/report/delReport',
    method: 'post',
    data
  })
}
export function gxpost(data){
  return request({
    url: '/inspect/report/gxpost',
    method: 'post',
    data
  })
}
