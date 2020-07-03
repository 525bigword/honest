import api from '@/utils/api'

export function list() {
  return api({
    url: '/processRick/getList',
    method: 'post',
  })
}

export function impFile(data) {
  return api({
    url: '/imp/importDuty',
    method: 'post',
    data
  })
}


export function add(data) {
  return api({
    url: '/processRick/add',
    method: 'post',
    data
  })
}


export function del(data) {
  return api({
    url: '/processRick/delete',
    method: 'post',
    data
  })
}

export function update(data) {
  return api({
    url: '/processRick/update',
    method: 'post',
    data
  })
}


export function findBy(data) {
  return api({
    url: '/processRick/findBy',
    method: 'post',
    data
  })
}









