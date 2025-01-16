package com.parting.dippin.domain.game.service;

import com.parting.dippin.domain.game.exception.GameCodeAndMessage;
import com.parting.dippin.domain.game.exception.GameTypeException;
import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.geo.GeoCoordinatesEntity;
import com.parting.dippin.entity.game.geo.repository.GeoCoordinatesRepository;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import com.parting.dippin.entity.game.player.repository.GamePlayerRepository;
import com.parting.dippin.entity.game.repository.GameRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameReader {

    private final GamePlayerRepository gamePlayerRepository;
    private final GameRepository gameRepository;
    private final GeoCoordinatesRepository geoCoordinatesRepository;

    public List<GamePlayerEntity> getPlayers(int gameId) {
        return this.gamePlayerRepository.findAllByGameId(gameId);
    }

    public GameEntity getGame(int gameId) {
        return this.gameRepository.findById(gameId)
            .orElseThrow(() -> GameTypeException.from(GameCodeAndMessage.GAME_NOT_FOUND));
    }

    public GamePlayerEntity getPlayer(int gameId, int memberId) {
        return this.gamePlayerRepository.findByGameIdAndMemberId(gameId, memberId)
            .orElseThrow(() -> GameTypeException.from(
                GameCodeAndMessage.NOT_GAME_MEMBER));
    }

    public String getMaxTime(int gameId, int memberId) {
        GeoCoordinatesEntity geoCoordinatesEntity = geoCoordinatesRepository.findOneByGameIdAndMemberIdOrderByTimeDesc(
            gameId, memberId);

        if (geoCoordinatesEntity == null) {
            return "00:00:00";
        }

        return geoCoordinatesEntity.getTime();
    }
}
