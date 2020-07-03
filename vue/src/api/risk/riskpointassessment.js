import api from '@/utils/api'

export function list() {
  return api({
    url: '/riskPointAssessment/getList',
    method: 'post'
  })
}


export function sync() {
  return api({
    url: '/riskPointAssessment/sync',
    method: 'post'
  })
}

export function getOne(data) {
  return api({
    url: '/riskPointAssessment/getOne',
    method: 'post',
    data
  })
}

export function findByPDeptId(data) {
  return api({
    url: '/riskPointAssessment/findByPDeptId',
    method: 'post',
    data
  })
}













