import request from '@/utils/api'
// 添加用户
export function add(data) {
    return request({
        url: '/spvduty/insert',
        method: 'post',
        data
    })
}

// 根据id删除用户
export function deleteSpvduty(data) {
    return request({
        url: '/spvduty/delete',
        method: 'post',
        data
    })
}

// 修改用户
export function update(data) {
    return request({
        url: '/spvduty/update',
        method: 'post',
        data
    })
}
export function updateStatus(data) {
    console.debug(data)
    return request({
        url: '/spvduty/updatestatus',
        method: 'post',
        params: {
            did: data.did,
            status: data.status,
            dutyType: data.dutyType,
            dCreateId: data.sysStaff.sid,
            bid: data.bid
        }
    })
}
// 根据用户对象进行查询用户
export function blist(pageNum, pageRow, did) {
    return request({
        url: '/spvback/get/' + pageNum + '/' + pageRow,
        method: 'get',
        params: {
            did: did,
            bid: 0
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

export function updatestatusall(back) {
    return request({
        url: '/spvback/updatestatusall',
        method: 'post',
        params: {
            backType: back.backType,
            bid: back.bid,
            status: back.status,
            sid: back.sid,
            bCreateId: 0
        }
    })
}
export function updatetong(data) {
    return request({
        url: '/spvduty/updatetong',
        method: 'post',
        data
    })
}


export function getDid() {
    return request({
        url: '/spvduty/getDid',
        method: 'post'
    })
}
export function impFile(data) {
    return request({
        url: '/imp/importDuty',
        method: 'post',
        data
    })
}