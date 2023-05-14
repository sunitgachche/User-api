package com.neosoft.userapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neosoft.userapi.models.UserDetail;

@Repository
public interface UserRepository extends JpaRepository<UserDetail, Integer> {
}
