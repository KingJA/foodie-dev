package com.imooc.service.impl;

import com.imooc.enums.YesOrNo;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.service.AddressService;

import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    public UserAddressMapper userAddressMapper;

    @Autowired
    public Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<UserAddress> getAddressList(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return this.userAddressMapper.select(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {
        //1.查找该用户是否已经地址
        //2.没有地址则设为默认地址，然后保存
        int isDefaultAddress = 0;
        List<UserAddress> addressList = getAddressList(addressBO.getUserId());
        if (addressList == null || addressList.isEmpty()) {
            isDefaultAddress = 1;
        }
        String id = sid.nextShort();
        UserAddress userAddress = new UserAddress();
        userAddress.setId(id);
        userAddress.setIsDefault(isDefaultAddress);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddressMapper.insert(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBO addressBO) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setId(addressBO.getAddressId());
        userAddress.setUpdatedTime(new Date());
        userAddressMapper.updateByPrimaryKeySelective(userAddress);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteNewUserAddress(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);
        userAddressMapper.delete(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void setAddressDefault(String userId, String addressId) {
        //把原先默认地址设为不默认
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setIsDefault(YesOrNo.YES.type);
        List<UserAddress> defalutAddressList = userAddressMapper.select(userAddress);
        for (UserAddress address : defalutAddressList) {
            address.setIsDefault(YesOrNo.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(address);
        }
        //把现在地址设为默认
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setId(addressId);
        defaultAddress.setUserId(userId);
        defaultAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);
    }

    @Override
    public UserAddress queryAddress(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);
        return userAddressMapper.selectOne(userAddress);

    }
}
