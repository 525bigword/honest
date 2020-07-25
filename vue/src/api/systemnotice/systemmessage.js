import request from '@/utils/api'
// 添加用户
export function add(data) {
    return request({
        url: '/systemmessage/insert',
        method: 'post',
        data
    })
}

// 根据id删除用户
export function deletesystemmessage(data) {
    return request({
        url: '/systemmessage/delete',
        method: 'post',
        data
    })
}

// 修改用户
export function update(data) {
    return request({
        url: '/systemmessage/update',
        method: 'post',
        data
    })
}

// 根据用户对象进行查询用户
export function list(pageNum, pageRow, articletitle, describe) {
    return request({
        url: '/systemmessage/get/' + pageNum + '/' + pageRow,
        method: 'get',
        params: { articletitle, describe }
    })
}

export function imageFile(data) {
    return request({
        url: '/imp/image',
        headers: { 'Content-Type': 'multipart/form-data' },
        method: 'post',
        data
    })
}