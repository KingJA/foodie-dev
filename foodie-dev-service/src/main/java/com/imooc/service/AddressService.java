package com.imooc.service;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {
    List<UserAddress> getAddressList(String userId);


    void addNewUserAddress(AddressBO addressBO);
    void updateUserAddress(AddressBO addressBO);
    void deleteNewUserAddress(String userId,String addressId);
    void setAddressDefault(String userId,String addressId);
    UserAddress queryAddress(String userId,String addressId);
}
