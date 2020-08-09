package com.amy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.amy.api.CommonResult;
import com.amy.domain.User;
import com.amy.mapper.UserMapper;
import com.amy.service.UserService;
import com.amy.utils.AESDecodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByOpenId(String openId) {
        return userMapper.getUserByOpenId(openId);
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public User getUserByIdAndOpenId(Long id, String openId) {
        return userMapper.getUserByIdAndOpenId(id,openId);
    }

    @Override
    public CommonResult bind(User user, String encrypData, String iv, String sessionKey) {
        String str = "";
        Map map = new HashMap();
        try {
            str = AESDecodeUtils.decrypt(encrypData, iv, sessionKey);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult(203,"获取手机号码失败");
        }
        JSONObject jsStrs = JSONObject.parseObject(str);
        String phoneNumber = jsStrs.getString("phoneNumber");
        if (!StringUtils.isNotBlank(phoneNumber)) {
            return new CommonResult(203,"获取手机号码失败");
        }
        user.setMobile(phoneNumber);
        this.updateUser(user);
        map.put("mobileFlag", true);
        map.put("user",user);
        return new CommonResult(200,"绑定手机号成功", map);
    }

    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }
}
