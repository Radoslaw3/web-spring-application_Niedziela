package com.wmusial.repositories;

import com.wmusial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmail(String search);

    @Query("SELECT u FROM User u " +
            "WHERE u.firstName LIKE :search " +
            "OR u.lastName LIKE :search " +
            "OR u.email LIKE :search")
    List<User> search(@Param("search") String search);
}
