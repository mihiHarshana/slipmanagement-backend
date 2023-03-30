package com.mhsoft.repo;


import com.mhsoft.model.DAOUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<DAOUser, Integer>{

        @Query(value = "SELECT * from user WHERE username = ?1 " , nativeQuery = true)
        DAOUser getUserByUserName(String userName);

        @Query(value = "SELECT * from user WHERE username = ?1 AND userpassword =?2" , nativeQuery = true)
        DAOUser getUserByNameAndPassword(String userName , String userPassword);
}
