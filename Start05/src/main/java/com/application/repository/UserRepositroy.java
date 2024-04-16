package com.application.repository;

import com.application.entity.User;
import com.application.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepositroy extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String userName);

    @Query("FROM User") // JPQL query to select all users
    List<User> findAllV2();

    @Query(" FROM User u WHERE u.id= :idUser")
    Optional<User> findByIdCustom(long idUser);

    @Query(" FROM User u WHERE u.id != :id and u.email= :email")
    Optional<User> findByEmailForOtherUser(long id, String email);

    @Query(" FROM User u WHERE u.id != :id and u.userName= :userName")
    Optional<User> findByUserNameForOtherUser(long id, String userName);

    Optional<User> findFirstByEmail(String username);

    Optional<User> findByEmailAndPassword(String email, String password);

    User findByRole(UserRole userRole);
}
