package com.parting.dippin.api.game.service;

import static com.parting.dippin.entity.game.enums.PlayerStatus.PARTICIPATION;
import static com.parting.dippin.entity.game.enums.ProgressStatus.*;
import static com.parting.dippin.entity.game.player.enums.ResultStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.parting.dippin.api.friend.service.FriendService;
import com.parting.dippin.api.game.dto.PostGameReqDto;
import com.parting.dippin.common.DatabaseTest;
import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.repository.TestGameRepository;
import com.parting.dippin.entity.member.MemberEntity;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DatabaseTest
class GameAllTerminateServiceTest {

    @Autowired
    private GameAllTerminateService gameAllTerminateService;
    @Autowired
    private TestGameRepository testGameRepository;
    @Autowired
    GameService gameService;
    @Autowired
    MemberReader memberReader;
    @Autowired
    FriendService friendService;

    @Test
    void terminateAll() {
        MemberEntity friend2 = memberReader.getMemberById(4);

        // data init script에 의해 member1과 member2는 이미 친구 관계
        friendService.addFriend(3, friend2.getMemberCode());

        PostGameReqDto postGameReqDto1 = generatePostGameReqDto(2);
        PostGameReqDto postGameReqDto2 = generatePostGameReqDto(4);

        gameService.requestGame(1, postGameReqDto1);
        gameService.requestGame(3, postGameReqDto2);

        gameAllTerminateService.terminateAll();

        List<GameEntity> games = testGameRepository.findAll();

        assertThat(games).hasSize(2)
                .allMatch(game -> game.getProgressStatus() == FINISHED);

        assertThat(games)
                .flatExtracting(GameEntity::getGamePlayerEntities)
                .allSatisfy(player -> {
                    assertThat(player.getPlayerStatus()).isEqualTo(PARTICIPATION);
                    assertThat(player.getResultStatus()).isNotEqualTo(UNDECIDED);
                });
    }

    private PostGameReqDto generatePostGameReqDto(int friendId) {
        return PostGameReqDto.builder()
                .friendId(friendId)
                .distance(3.0)
                .timeLimit(30)
                .build();
    }
}