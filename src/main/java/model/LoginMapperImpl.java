package model;

import org.apache.ibatis.session.SqlSession;

public class LoginMapperImpl {
    public int validateLogin(String email, String password) {
        try (SqlSession session = MyBatisUtil.getSqlSession()){
            LoginMapper loginMapper = session.getMapper(LoginMapper.class);
            return  loginMapper.validateLogin(email, password);
        }
    }

    public int registerUser(String emailOrPhone, String password){
        SqlSession session = MyBatisUtil.getSqlSession();
        try {
            LoginMapper loginMapper = session.getMapper(LoginMapper.class);
            int result = loginMapper.registerUser(emailOrPhone, password);
            session.commit();
            return result;
        } finally {
            session.close();
        }
    }
}