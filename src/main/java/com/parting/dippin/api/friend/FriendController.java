package com.parting.dippin.api.friend;

import com.parting.dippin.api.friend.dto.GetFriendsResDto;
import com.parting.dippin.api.friend.service.FriendReader;
import com.parting.dippin.api.friend.service.FriendService;
import com.parting.dippin.core.base.BaseResponse;
import com.parting.dippin.core.common.annotation.LoggedInMemberId;
import com.parting.dippin.domain.friend.dto.FriendDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/friends")
@RestController()
public class FriendController {

    private final FriendReader friendReader;
    private final FriendService friendService;

    @GetMapping(value = "")
    public BaseResponse<GetFriendsResDto> getFriends(
            @LoggedInMemberId Integer memberId
    ) {
        List<FriendDto> friends = friendReader.getFriends(memberId);
        GetFriendsResDto resDto = new GetFriendsResDto(friends);

        return BaseResponse.success(resDto);
    }

    @PostMapping()
    public BaseResponse<Void> addFriend(
            @LoggedInMemberId Integer memberId,
            @RequestBody int friendId
    ) {
        friendService.addFriend(memberId, friendId);

        return BaseResponse.success();
    }
}
