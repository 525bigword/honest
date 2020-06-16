import request from '@/utils/api'
// 添加用户
export function add(data) {
    return request({
        url: '/datakamset/add',
        method: 'post',
        data
    })
}

// 根据id删除用户
export function deleteUser(uid) {
    return request({
        url: '/datakamset/delete',
        method: 'get',
        params: { uid }
    })
}

// 修改用户
export function update(data) {
    return request({
        url: '/datakamset/update',
        method: 'post',
        data
    })
}

// 根据用户对象进行查询用户
export function list(pageNum, pageRow) {
    return request({
        url: '/datacollection/get/' + pageNum + '/' + pageRow,
        method: 'get'
    })
}