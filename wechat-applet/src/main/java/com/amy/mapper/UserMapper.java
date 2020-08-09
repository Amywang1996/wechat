package com.amy.mapper;

import com.amy.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

  public  User getUserByOpenId(@Param("openId") String openId);

  public  int insertUser(User user);

   public User getUserByIdAndOpenId(@Param("id") Long id,@Param("openId") String openId);

 public int updateUser(User user);
}
