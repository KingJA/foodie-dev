package com.imooc.service;

import com.imooc.pojo.Stu;

/**
 * Description:TODO
 * Create Time:2020/9/1 0001 下午 4:10
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface StuService {
    public Stu getStuInfo(int id);
    public void saveStu();
    public void updateStu(int id);
    public void deleteStu(int id);
}
