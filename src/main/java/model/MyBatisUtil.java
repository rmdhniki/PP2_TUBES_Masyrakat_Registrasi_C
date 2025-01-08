package model;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * MyBatisUtil adalah kelas utilitas untuk mengelola konfigurasi MyBatis
 * dan menyediakan SqlSession untuk interaksi dengan database.
 */
public class MyBatisUtil {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // Baca konfigurasi MyBatis dari file mybatis-config.xml
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Gagal membaca konfigurasi MyBatis", e);
        }
    }

    /**
     * Mendapatkan SqlSession untuk berinteraksi dengan database.
     *
     * @return SqlSession
     */
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }

    /**
     * Mendapatkan SqlSessionFactory jika diperlukan secara langsung.
     *
     * @return SqlSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}