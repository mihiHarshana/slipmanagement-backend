package com.mhsoft.repo;

import com.mhsoft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * from user WHERE username = ?1 " , nativeQuery = true)
    User getUserByUserName(String userName);

    @Query(value = "SELECT * from user WHERE username = ?1 AND password =?2" , nativeQuery = true)
    User getUserByNameAndPassword(String userName , String userPassword);


}
