package Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.User;

public interface MapperUser {

    @Select("SELECT * FROM users WHERE id = #{id}")
    User getById(Integer id);

    @Select("SELECT * FROM users WHERE email = #{email}")
    @Results({
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "roleId", column = "role_id"),
    })
    User getByEmail(String email);

    @Insert("INSERT INTO users(name, email, password, address, birth_date, created_at, role_id, photo_path, is_verified) " +
            "VALUES(#{name}, #{email}, #{password}, #{address}, #{birthDate}, #{createdAt}, COALESCE(#{roleId}, 2),#{photoPath}, #{isVerified})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);
    @Update("UPDATE users SET password = #{password} WHERE email = #{email}")
    void updatePassword(@Param("email") String email,@Param("password") String password );

    @Update("UPDATE users SET name = #{name}, address = #{address}, birth_date = #{birthDate}, photo_path = #{photoPath} WHERE id = #{id}")
    int updateUser(User user);

    @Select("SELECT password FROM users WHERE email = #{email}")
    String getPasswordByEmail(@Param("email") String email);


    // Update the verification status of the user
    @Update("UPDATE users SET is_verified = #{status} WHERE email = #{email}")
    void updateVerificationStatus(@Param("email") String email, @Param("status") Integer status);

    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
            @Result(property = "email", column = "email"),
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "photoPath", column = "photo_path"),
            @Result(property = "birthDate", column = "birth_date"),
            @Result(property = "isVerified", column = "is_verified"),
            @Result(property = "createdAt", column = "created_at")

    })
    User getUserById(Integer id);

    // Get all users
    @Select("SELECT u.id, u.name, u.email, u.address, u.birth_date, r.name AS role_name FROM users u LEFT JOIN role r ON u.role_id = r.id")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "address", column = "address"),
            @Result(property = "birthDate", column = "birth_date"),
            @Result(property = "roleName", column = "role_name")
    })
    List<User> getAllUsers();

    // Delete user by ID
    @Delete("DELETE FROM users WHERE id = #{id}")
    void deleteUser(int id);
}