package com.parting.dippin.api.friend;

import com.parting.dippin.api.friend.dto.GetFriendsResDto;
import com.parting.dippin.api.friend.service.FriendReader;
import com.parting.dippin.api.friend.service.FriendService;
import com.parting.dippin.core.base.BaseResponse;
import com.parting.dippin.domain.friend.dto.FriendDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/friends")
@RestController()
public class FriendController {

    private final FriendReader friendReader;
    private final FriendService friendService;

    @GetMapping(value = "")
    public BaseResponse<GetFriendsResDto> getFriends() {
        List<FriendDto> friends = friendReader.getFriends(1);
        GetFriendsResDto resDto = new GetFriendsResDto(friends);

        return BaseResponse.success(resDto);
    }

    @PostMapping()
    public BaseResponse addFriend(@RequestBody int friendId) {
        friendService.addFriend(1, friendId);
        return BaseResponse.success();
    }
}
