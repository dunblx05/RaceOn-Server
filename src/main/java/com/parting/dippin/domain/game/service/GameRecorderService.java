package com.parting.dippin.domain.game.service;

import com.parting.dippin.entity.game.geo.GeoCoordinatesEntity;
import com.parting.dippin.entity.game.geo.repository.GeoCoordinatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameRecorderService {

    private final GeoCoordinatesRepository geoCoordinatesRepository;

    public void recordGeoData(int gameId, int memberId, String time, double latitude, double longitude, double distance) {
        GeoCoordinatesEntity geoCoordinatesEntity = GeoCoordinatesEntity.builder()
            .gameId(gameId)
            .memberId(memberId)
            .time(time)
            .latitude(latitude)
            .longitude(longitude)
            .distance(distance)
            .build();

        geoCoordinatesRepository.save(geoCoordinatesEntity);;
    }
}
