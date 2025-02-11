
package com.hello.ticketlink.user.repository;

import com.hello.ticketlink.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByusername(String username);
}