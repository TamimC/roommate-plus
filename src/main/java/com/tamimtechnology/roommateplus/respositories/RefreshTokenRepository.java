package com.tamimtechnology.roommateplus.respositories;

import com.tamimtechnology.roommateplus.model.BusinessEntities.User;
import com.tamimtechnology.roommateplus.model.SecurityEntities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    boolean existsByToken (String token);
    @Query(value = "SELECT r.user_id FROM refresh_token r WHERE r.token = :token", nativeQuery = true)
    Long findUserIdByToken (@Param("token") String token);
    void deleteAllByUser (User user);
    RefreshToken findRefreshTokenByToken (String token);
}
