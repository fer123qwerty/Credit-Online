package com.system.creditOnline.repository;

import com.system.creditOnline.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
