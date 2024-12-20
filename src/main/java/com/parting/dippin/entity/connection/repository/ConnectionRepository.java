package com.parting.dippin.entity.connection.repository;

import com.parting.dippin.entity.connection.ConnectionStatusEntity;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

public interface ConnectionRepository extends CrudRepository<ConnectionStatusEntity, Integer> {

    ConnectionStatusEntity save(ConnectionStatusEntity connectionStatus);

    ConnectionStatusEntity findByMemberId(int memberId);

    @NotNull
    List<ConnectionStatusEntity> findAllById(@NotNull Iterable<Integer> memberIds);
}
