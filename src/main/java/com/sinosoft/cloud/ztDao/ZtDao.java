package com.sinosoft.cloud.ztDao;


import com.sinosoft.cloud.entity.StudentEntity;
import com.sinosoft.cloud.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

public interface ZtDao {
    void createUser(UserEntity user);
    UserEntity getUser(String name);
    void createStudent(StudentEntity student);
    StudentEntity getStudent(String name);
    void addBaseCode(@Param("param") String param);
}
