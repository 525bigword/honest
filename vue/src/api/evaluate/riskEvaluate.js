import api from '@/utils/api'

export function getData() {
  return api({
    url: '/riskPointAssessment/getList',
    method: 'post'
  })
}







