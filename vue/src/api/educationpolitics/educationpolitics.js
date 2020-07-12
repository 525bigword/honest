import request from '@/utils/api'
export function findAllEducation(){
  return request({
    url: '/education/politics/findAllEducation',
    method: 'post'
  })
}
export function addEcucation(data){
  return request({
    url: '/education/politics/addEcucation',
    method: 'post',
    data
  })
}export function delEcucation(data){
  return request({
    url: '/education/politics/delEcucation',
    method: 'post',
    data
  })
}export function updateEducation(data){
  return request({
    url: '/education/politics/updateEducation',
    method: 'post',
    data
  })
}export function findwhereEducation(data){
  return request({
    url: '/education/politics/findwhereEducation',
    method: 'post',
    data
  })
}
