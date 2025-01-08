package model;

import org.apache.ibatis.annotations.Param;

public interface LoginMapper {
    int validateLogin(@Param("email") String email, @Param("password") String password);

    int registerUser(@Param("emailOrPhone") String emailOrPhone, @Param("password") String password);
}