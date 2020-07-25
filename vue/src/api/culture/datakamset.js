import request from '@/utils/api'
// 添加用户
export function add(data) {
    return request({
        url: '/datacollection/insert',
        method: 'post',
        data
    })
}

// 根据id删除用户
export function deleteDatakamset(data) {
    return request({
        url: '/datacollection/delete',
        method: 'post',
        data
    })
}
export function imp(data) {
    return request({
        url: '/imp/import',
        method: 'post',
        data
    })
}
export function vimp(data) {
    return request({
        url: '/imp/vido',
        method: 'post',
        data
    })
}

// 修改用户
export function update(data) {
    return request({
        url: '/datacollection/update',
        method: 'post',
        data
    })
}

// 根据用户对象进行查询用户
export function list(pageNum, pageRow, dTitle) {
    return request({
        url: '/datacollection/get/' + pageNum + '/' + pageRow,
        method: 'get',
        params: { dTitle }
    })
}