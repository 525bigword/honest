import request from '@/utils/api'
// 添加用户
export function add(data) {
    return request({
        url: '/wind/insert',
        method: 'post',
        data
    })
}

// 根据id删除用户
export function deleteWind(data) {
    return request({
        url: '/wind/delete',
        method: 'post',
        data
    })
}

// 修改用户
export function update(data) {
    return request({
        url: '/wind/update',
        method: 'post',
        data
    })
}
export function melist(data) {
    return request({
        url: '/sysmechanism/findSysMechanisms/',
        method: 'post',
        data
    })
}

export function stafflist(mid) {
    return request({
        url: '/SysStaff/findSysStaffByMid/',
        method: 'post',
        params: {
            mid: mid
        }
    })
}
export function getDid() {
    return request({
        url: '/spvduty/getDid',
        method: 'post'
    })
}

// 根据用户对象进行查询用户
export function list(pageNum, pageRow, wTitle) {
    return request({
        url: '/wind/get/' + pageNum + '/' + pageRow,
        method: 'get',
        params: { wTitle }
    })
}