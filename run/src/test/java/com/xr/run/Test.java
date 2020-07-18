/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: Test
 * Author:   TSYH
 * Date:     2020-07-12 0:06
 * Description:
 * History:
 * <author>     <time>       <version>     <desc>
 * 作者姓名    修改时间     版本号       描述
 */
package com.xr.run;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.dao.SystemMessageMapper;
import com.xr.run.entity.SystemMessage;
import com.xr.run.service.SystemMessageService;
import com.xr.run.service.impl.HomePageSeviceImpl;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author TSYH
 * @create 2020-07-12
 * @since 1.0.0
 */
@SpringBootTest
public class Test {
    @Autowired
    private HttpServletRequest request;
//    @org.junit.jupiter.api.Test
//    public void a(){
//        String uri= request.getRealPath("run");
//        System.out.println(uri);
////        uri=uri.substring(0,uri.length()-10);
//        System.out.println(uri);
//    }
    /**
     * 产生盐值
     * @return
     */
    public static String getSalt(){
        //生成盐需要存入数据库中的
        String salt =new SecureRandomNumberGenerator().nextBytes().toHex();
        return salt;
    }

    /**
     * 根据盐值和原始密码MD5加密n次
     * @param originalPassword 原始密码
     * @param salt 盐
     * @param n 次数
     * @return
     */
    public static String getMD5Password(String originalPassword,String salt,Integer n){
        System.out.println(ByteSource.Util.bytes(originalPassword));
        String md5Password = new Md5Hash(originalPassword,n).toString();
        return md5Password;
    }

//    // 测试
//    public static void main(String[] args) {
//        //生成盐（部分，需要存入数据库中）
////        String salt = getSalt();
////        System.out.println(salt);
//        //将原始密码加盐（上面生成的盐），并且用md5算法加密三次，将最后结果存入数据库中
//        //String pwd = getMD5Password("123456",salt,1);
//        SimpleHash simpleHash = new SimpleHash("admin");
//        System.out.println(simpleHash);
//    }
}