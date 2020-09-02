package com.imooc.service.impl;

import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.MD5Util;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import tk.mybatis.mapper.entity.Example;

/**
 * Description:TODO
 * Create Time:2020/9/1 0001 下午 4:37
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUserIsExist(String username) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        return usersMapper.selectOneByExample(example)!=null;
    }

    @Override
    public void createUser(UserBO userBO) {
        Users users = new Users();
        String id = sid.nextShort();
        users.setId(id);
        users.setBirthday(DateUtil.stringToDate("1990-01-22"));
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());
        users.setUsername(userBO.getUsername());
        users.setNickname(userBO.getUsername());
        users.setPassword(MD5Util.md5(userBO.getPassword()));
        users.setFace("https://");
        users.setSex(1);
        usersMapper.insert(users);

    }
}
