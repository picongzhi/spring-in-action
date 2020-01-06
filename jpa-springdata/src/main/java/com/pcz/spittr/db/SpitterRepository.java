package com.pcz.spittr.db;

import com.pcz.spittr.domain.Spitter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author picongzhi
 */
public interface SpitterRepository extends JpaRepository<Spitter, Long>, SpitterSweeper {
    Spitter findByUsername(String username);

    List<Spitter> findByUsernameOrFullNameLike(String username, String fullName);
}
