package com.parting.dippin.entity.token.repository;

import com.parting.dippin.entity.token.FcmTokenEntity;
import com.parting.dippin.entity.token.FcmTokenId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmTokenRepository extends JpaRepository<FcmTokenEntity, FcmTokenId>,
    QFcmTokenRepository {

    Optional<FcmTokenEntity> findByToken(String token);

    void deleteByToken(String token);
}
