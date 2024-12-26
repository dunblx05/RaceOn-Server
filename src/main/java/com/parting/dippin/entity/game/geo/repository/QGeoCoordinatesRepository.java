package com.parting.dippin.entity.game.geo.repository;

public interface QGeoCoordinatesRepository {

    String findTimeByIdOrderByTimeDESC(int gameId, int memberId);
}
