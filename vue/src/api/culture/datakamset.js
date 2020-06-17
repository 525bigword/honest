import request from '@/utils/api'
// 添加用户
export function add(data) {
    return request({
        url: '/datacollection/insert',
        method: 'post',
        params: {
            dTitle: data.dtitle,
            dFile: data.dFile,
            dFileName: data.dFileName,
            dCreateId: data.sysStaff.sid,
            dStatus: data.dstatus
        }
    })
}

// 根据id删除用户
export function deleteDatakamset(did) {
    return request({
        url: '/datakamset/delete',
        method: 'get',
        params: { did }
    })
}
export function imp(data) {
    return request({
        url: '/imp/import',
        method: 'post',
        data
    })
}

// 修改用户
export function update(data) {
    return request({
        url: '/datacollection/update',
        method: 'post',
        params: {
            dTitle: data.dtitle,
            dFile: data.dFile,
            dFileName: data.dFileName,
            did: data.did,
            dStatus: data.dstatus
        }
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