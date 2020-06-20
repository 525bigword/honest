import request from '@/utils/api'
// 添加用户
export function add(data) {
    return request({
        url: '/riskpointwarning/insert',
        method: 'post',
        params: {
            wTitle: data.wtitle,
            wContent: data.wcontent,
            numId: data.numId,
            wCreateId: data.sysStaff.sid
        }
    })
}

// 根据id删除用户
export function deleteRiskpointwarning(data) {
    return request({
        url: '/riskpointwarning/delete',
        method: 'post',
        data
    })
}

// 修改用户
export function update(data) {
    return request({
        url: '/riskpointwarning/update',
        method: 'post',
        params: {
            wTitle: data.wtitle,
            wContent: data.wcontent,
            wid: data.wid
        }
    })
}

// 根据用户对象进行查询用户
export function list(pageNum, pageRow, wTitle, wContent) {
    return request({
        url: '/riskpointwarning/get/' + pageNum + '/' + pageRow,
        method: 'get',
        params: { wTitle, wContent }
    })
}