package com.parting.dippin.domain.game.service.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parting.dippin.api.game.service.GameSocketService;
import com.parting.dippin.domain.game.dto.socket.GameProcessReqDto;
import com.parting.dippin.domain.game.dto.socket.GameProcessResDto;
import com.parting.dippin.entity.game.geo.GeoCoordinatesEntity;
import com.parting.dippin.entity.game.geo.repository.GeoCoordinatesRepository;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import com.parting.dippin.entity.game.player.repository.GamePlayerRepository;
import com.parting.dippin.entity.game.repository.GameRepository;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameProcessorService implements GameSocketService {

    private final GamePlayerRepository gamePlayerRepository;
    private final GeoCoordinatesRepository geoCoordinatesRepository;
    private final GameRepository gameRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public GameProcessResDto invoke(int gameId, int memberId, String data)
        throws JsonProcessingException, NoSuchElementException {

        GameProcessReqDto reqDto = this.objectMapper.readValue(data, GameProcessReqDto.class);

        // TODO - 게임 시작 전인데, 들어올 경우 400 날려줘야함

        this.saveRecord(gameId, memberId, reqDto);

        return new GameProcessResDto(gameId, memberId, reqDto.getTime(), reqDto.getLatitude(),
            reqDto.getLongitude(), reqDto.getDistance());
    }

    /**
     * 기록 저장
     */
    private void saveRecord(int gameId, int memberId, GameProcessReqDto reqDto) {
        GamePlayerEntity gamePlayerEntity = this.gamePlayerRepository.findByGameIdAndMemberId(
            gameId,
            memberId);

        gamePlayerEntity.process(reqDto.getDistance(), reqDto.getAvgSpeed(), reqDto.getMaxSpeed());

        GeoCoordinatesEntity geoCoordinatesEntity = GeoCoordinatesEntity.from(gameId, memberId,
            reqDto.getTime(), reqDto.getLatitude(), reqDto.getLongitude(), reqDto.getDistance());

        geoCoordinatesRepository.save(geoCoordinatesEntity);
        gamePlayerRepository.save(gamePlayerEntity);
    }
}
