package com.sinosoft.cloud.hxDao;


import com.sinosoft.cloud.entity.StudentEntity;
import com.sinosoft.cloud.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

public interface HxDao {
    void createUser(UserEntity user);
    UserEntity getUser(String name);
    void createStudent(StudentEntity student);
    StudentEntity getStudent(String name);
    void addLdcom(@Param("param") String param);
}
