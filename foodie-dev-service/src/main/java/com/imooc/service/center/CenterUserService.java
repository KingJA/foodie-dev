package com.imooc.service.center;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.pojo.bo.center.CenterUserBO;

/**
 * Description:TODO
 * Create Time:2020/9/1 0001 下午 4:10
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface CenterUserService {
    public Users queryUserInfo(String userId);
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    Users updateUserFace(String userId, String faceUrl);
}
