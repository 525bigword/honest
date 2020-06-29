import request from '@/utils/api'
export function listLetterByid(data){
  return request({
    url: '/daily/letter/listLetterByid',
    method: 'post',
    data
  })
}
export function tjshme(data){
  return request({
    url: '/daily/letter/tjshme',
    method: 'post',
    data
  })
}
