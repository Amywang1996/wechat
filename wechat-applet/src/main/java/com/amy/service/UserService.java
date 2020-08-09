package com.amy.service;

import com.amy.api.CommonResult;
import com.amy.domain.User;

public interface UserService {
   public User getUserByOpenId(String openId);

    public int insertUser(User user);

  public  User getUserByIdAndOpenId(Long id, String openId);

   public CommonResult bind(User user, String encrypData, String iv, String sessionKey);
}
