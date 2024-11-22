package com.parting.dippin.entity.friends.repository;

import com.parting.dippin.entity.friends.FriendsEntity;
import com.parting.dippin.entity.friends.FriendsId;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendsRepository extends JpaRepository<FriendsEntity, FriendsId>, QFriendsRepository { }
