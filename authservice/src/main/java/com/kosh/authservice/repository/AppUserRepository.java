package com.kosh.authservice.repository;

import com.kosh.authservice.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    @Query(value = " SELECT * from app_user where email = :email limit 1 ", nativeQuery = true)
    AppUser findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

}
