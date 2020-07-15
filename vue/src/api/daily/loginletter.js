import request from '@/utils/api'

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
  })}
  /*登录举报*/
  export function finddb(data){
    return request({
      url: '/daily/letter/finddb',
      method: 'post',
      data
    })
  }
