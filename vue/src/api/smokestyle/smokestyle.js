import request from '@/utils/api'
export function findAllSmokestyle(data){
  return request({
    url: '/smokestyle/smoke/findAllSmokestyle',
    method: 'post',
    data
  })
}
export function addSmokestyle(data){
  return request({
    url: '/smokestyle/smoke/addSmokestyle',
    method: 'post',
    data
  })
}
export function delSmokestyle(data){
  return request({
    url: '/smokestyle/smoke/delSmokestyle',
    method: 'post',
    data
  })
}
export function findSmokestyle(data){
  return request({
    url: '/smokestyle/smoke/findSmokestyle',
    method: 'post',
    data
  })
}
export function gxSmokestyle(data){
  return request({
    url: '/smokestyle/smoke/gxSmokestyle',
    method: 'post',
    data
  })
}

