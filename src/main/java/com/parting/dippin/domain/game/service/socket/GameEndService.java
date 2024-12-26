package com.parting.dippin.domain.game.service.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parting.dippin.api.game.service.GameSocketService;
import com.parting.dippin.domain.game.dto.socket.GameEndReqDto;
import com.parting.dippin.domain.game.dto.socket.GameEndResDto;
import com.parting.dippin.domain.game.exception.InvalidGameEndException;
import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.enums.ProgressStatus;
import com.parting.dippin.entity.game.geo.GeoCoordinatesEntity;
import com.parting.dippin.entity.game.geo.repository.GeoCoordinatesRepository;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import com.parting.dippin.entity.game.player.repository.GamePlayerRepository;
import com.parting.dippin.entity.game.repository.GameRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameEndService implements GameSocketService {

    private final GamePlayerRepository gamePlayerRepository;
    private final GeoCoordinatesRepository geoCoordinatesRepository;
    private final GameRepository gameRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public GameEndResDto invoke(int gameId, int memberId, String data)
        throws Exception {

        GameEndReqDto reqDto = this.objectMapper.readValue(data, GameEndReqDto.class);

        this.saveRecord(gameId, memberId, reqDto);

        GameEntity gameEntity = this.gameRepository.findGameEntityByGameIdAndProgressStatus(gameId,
            ProgressStatus.ONGOING).orElseThrow();

        boolean isFinished = gameEntity.isFinished(reqDto.getDistance());

        if (!isFinished) {
            throw new InvalidGameEndException();
        }

        gameEntity.finish();
        this.gameRepository.save(gameEntity);

        this.finishGame(gameId, memberId, reqDto);

        return new GameEndResDto(gameId, memberId);
    }

    /**
     * 기록 저장
     */
    private void saveRecord(int gameId, int memberId, GameEndReqDto reqDto) {
        GeoCoordinatesEntity geoCoordinatesEntity = GeoCoordinatesEntity.from(gameId, memberId,
            reqDto.getTime(), reqDto.getLatitude(), reqDto.getLongitude(), reqDto.getDistance());

        geoCoordinatesRepository.save(geoCoordinatesEntity);
    }

    /**
     * 게임 종료
     */
    private void finishGame(int gameId, int memberId, GameEndReqDto reqDto) {
        List<GamePlayerEntity> gamePlayerEntities = this.gamePlayerRepository.findByGameId(gameId);

        for (GamePlayerEntity gamePlayer : gamePlayerEntities) {
            if (memberId == gamePlayer.getMemberId()) {
                gamePlayer.win(reqDto.getDistance(), reqDto.getAvgSpeed(), reqDto.getMaxSpeed(),
                    reqDto.getTime());
                continue;
            }

            String finishedTime = geoCoordinatesRepository.findTimeByIdOrderByTimeDESC(gameId,
                gamePlayer.getMemberId());

            gamePlayer.lose(finishedTime);
        }

        gamePlayerRepository.saveAll(gamePlayerEntities);
    }
}
