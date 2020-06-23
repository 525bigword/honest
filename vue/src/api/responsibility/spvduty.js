import request from '@/utils/api'
// 添加用户
export function add(data) {
    return request({
        url: '/spvduty/insert',
        method: 'post',
        params: {
            dnumId: data.dnumId,
            dutyType: data.dutyType,
            dutyTitle: data.dutyTitle,
            dutyContent: data.dutyContent,
            bid: data.bmid,
            dutyAccessory: data.dutyAccessory,
            dutyAccessoryName: data.dutyAccessoryName,
            dCreateId: data.sysStaff.sid,
            status: data.status
        }
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
        params: {
            did: data.did,
            dutyType: data.dutyType,
            dutyTitle: data.dutyTitle,
            dutyContent: data.dutyContent,
            bid: data.bmid,
            dutyAccessory: data.dutyAccessory,
            dutyAccessoryName: data.dutyAccessoryName,
            status: data.status
        }
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
        params: { did }
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

export function updatestatusall(back, status) {
    return request({
        url: '/spvback/updatestatusall',
        method: 'post',
        params: {
            backType: back.backType,
            status: status,
            sid: back.cid
        }
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