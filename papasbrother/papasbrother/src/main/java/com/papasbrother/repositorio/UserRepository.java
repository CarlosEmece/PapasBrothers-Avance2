package com.papasbrother.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.papasbrother.modelo.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByCorreo(String correo);

}
