import request from '@/utils/api'
// 添加用户
export function add(data) {
    return request({
        url: '/spvback/insert',
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


// 修改用户
export function update(data) {
    console.debug(data)
    return request({
        url: '/spvback/update',
        method: 'post',
        data
    })
}
export function updateStatus(data) {
    return request({
        url: '/spvback/updatestatus',
        method: 'post',
        params: {
            sid: data.sid,
            status: data.status
        }
    })
}
export function deleteSid(data) {
    return request({
        url: '/spvback/delete',
        method: 'post',
        params: {
            sid: data
        }
    })
}


// 根据用户对象进行查询用户
export function list(pageNum, pageRow, bid) {
    return request({
        url: '/spvback/get/' + pageNum + '/' + pageRow,
        method: 'get',
        params: {
            bid: bid,
            did: 0
        }
    })
}

export function impFile(data) {
    return request({
        url: '/imp/importDuty',
        method: 'post',
        data
    })
}