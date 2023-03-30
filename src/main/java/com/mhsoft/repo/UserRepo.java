package com.mhsoft.repo;


import com.mhsoft.model.DAOUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface UserRepo extends JpaRepository<DAOUser, Integer>{

        @Query(value = "SELECT * from user WHERE username = ?1 " , nativeQuery = true)
        DAOUser getUserByUserName(String userName);

        @Query(value = "SELECT * from user WHERE username = ?1 AND userpassword =?2" , nativeQuery = true)
        DAOUser getUserByNameAndPassword(String userName , String userPassword);

        @Modifying
        @Transactional
        @Query (value = "INSERT INTO table_name (username, password, usertype, userstatus)" +
                "VALUES (value1, value2, value3, value4) where id =?1" , nativeQuery = true)
        int updateUserDetailsById (int userid);
}
