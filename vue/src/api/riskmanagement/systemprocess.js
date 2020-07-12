import request from '@/utils/api'
// 添加用户
export function add(data) {
    return request({
        url: '/systemprocess/insert',
        method: 'post',
        data
    })
}

// 根据id删除用户
export function deleteSystemprocess(data) {
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
        data
    })
}

// 根据用户对象进行查询用户
export function list(pageNum, pageRow, sysTitle, sysContent) {
    return request({
        url: '/systemprocess/get/' + pageNum + '/' + pageRow,
        method: 'get',
        params: { sysTitle, sysContent }
    })
}