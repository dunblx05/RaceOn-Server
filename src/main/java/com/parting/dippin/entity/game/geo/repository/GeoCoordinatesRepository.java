package com.parting.dippin.entity.game.geo.repository;

import com.parting.dippin.entity.game.geo.GeoCoordinatesEntity;
import com.parting.dippin.entity.game.geo.GeoCoordinatesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoCoordinatesRepository extends
    JpaRepository<GeoCoordinatesEntity, GeoCoordinatesId>, QGeoCoordinatesRepository {
}
