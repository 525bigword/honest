import request from '@/utils/api'
// 添加用户
export function add(data) {
    return request({
        url: '/systemprocess/insert',
        method: 'post',
        params: {
            sysTitle: data.sysTitle,
            sysContent: data.sysContent,
            sysCreateId: data.sysStaff.sid
        }
    })
}

// 根据id删除用户
export function deleteSpvduty(data) {
    return request({
        url: '/systemprocess/delete',
        method: 'post',
        data
    })
}

// 修改用户
export function update(data) {
    return request({
        url: '/systemprocess/update',
        method: 'post',
        params: {
            sysTitle: data.sysTitle,
            sysContent: data.sysContent,
            sysid: data.sysid
        }
    })
}

// 根据用户对象进行查询用户
export function list(pageNum, pageRow, dutyTitle) {
    return request({
        url: '/spvduty/get/' + pageNum + '/' + pageRow,
        method: 'get',
        params: { dutyTitle }
    })
}