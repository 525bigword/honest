import api from '@/utils/api'

export function list(pageNum,pageSize) {
  return api({
    url: '/postRiskCombing/getList',
    method: 'post',
    params: { pageNum, pageSize }
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

export function del(pcid) {
  return api({
    url: '/postRiskCombing/delete',
    method: 'post',
    params:{pcid}
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
    /* params: {
       pdeptid: data.pdeptid,
       pinfomationid: data.pinfomationid,
       pgrade: data.pgrade}*/

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

export function getSysPostByMid(pdeptid) {
  return api({
    url: '/postRiskCombing/getSysPostByMid',
    method: 'post',
    params: {pdeptid}
  })
}










