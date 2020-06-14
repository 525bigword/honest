import api from '@/utils/api'

export function getInfo(token) {
    return api({
        url: '/SysStaff/testGetInfo',
        method: 'get',
        params: { token }
    })
}