package com.stussy.stussyclone20220929jaehyo.repository;

import com.stussy.stussyclone20220929jaehyo.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountRepository {
    public int save(User user);

    public int updateUserOauth2(User user);
    public User findUserByEmail(String email);
}
