import request from '@/utils/api'
import qs from 'qs'
/**
 * 自定义上传
 * @param {*} fileobj
 */
export function
fileUpload(data){
 // let param = new FormData();
  // 上传文件对象 名称file与后台控制器参数要一致
//  param.append('filename',fileobj.filename);
  return request({
    method: 'post',
    // 上传地址
    url: '/duty/talk/uploading',
    // 定义上传头
    headers: {'Content-Type':'multipart/form-data'},
    data
  });
}
export function list(data){
  return request({
    method: 'post',
    url: '/daily/supervisionfiling/list',
    data
  });
}
export function findbytitle(data){
  return request({
    method: 'post',
    url: '/daily/supervisionfiling/findbytitle',
    data
  });
}
export function addsuper(data){
  return request({
    method: 'post',
    url: '/daily/supervisionfiling/addsupervisionfiling',
    data
  });
}

export function dele(data){
  return request({
    method: 'post',
    url: '/daily/supervisionfiling/delesupervision',
    data
  });
}
export function tjshme(data){
  return request({
    method: 'post',
    url: '/daily/supervisionfiling/tjshme',
    data
  });
}
export function gxme(data){
  return request({
    method: 'post',
    url: '/daily/supervisionfiling/updatesupervision',
    data
  });
}
