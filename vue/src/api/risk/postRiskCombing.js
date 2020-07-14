import api from '@/utils/api'

export function list() {
  return api({
    url: '/postRiskCombing/getList',
    method: 'post'
  })
}

/*新增工作计划*/
export function add(data) {
  return api({
    url: '/postRiskCombing/add',
    method: 'post',
    data
  })
}

export function del(data) {
  return api({
    url: '/postRiskCombing/delete',
    method: 'post',
    data
  })
}

export function update(data) {
  return api({
    url: '/postRiskCombing/update',
    method: 'post',
    data
  })
}


export function findBy(data) {
  return api({
    url: '/postRiskCombing/findBy',
    method: 'post',
    data
  })
}

export function getAllPost() {
  return api({
    url: '/postRiskCombing/getAllPost',
    method: 'post'
  })
}

export function getAllMechanism() {
  return api({
    url: '/postRiskCombing/getAllMechanism',
    method: 'post'
  })
}

export function getAll() {
  return api({
    url: '/sysmechanism/getAll',
    method: 'get'
  })
}

export function getAllMechanismByParent(data) {
  return api({
    url: '/postRiskCombing/getAllMechanismByParent',
    method: 'post',
    data
  })
}

export function getSysPostByMid(data) {
  return api({
    url: '/postRiskCombing/getSysPostByMid',
    method: 'post',
    data
  })
}










