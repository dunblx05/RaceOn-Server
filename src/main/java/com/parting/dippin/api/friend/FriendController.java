package com.parting.dippin.api.friend;

import com.parting.dippin.core.base.BaseResponse;
import com.parting.dippin.domain.friend.dao.IFriendDAO;
import com.parting.dippin.domain.friend.dto.FriendDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("friend")
@RestController()
public class FriendController {

    private final IFriendDAO friendDAO;

    @GetMapping(path = "{memberId}")
    public BaseResponse<List<FriendDto>> getFriendList(
        @PathVariable Integer memberId
    ) {
        return BaseResponse.success(friendDAO.getFriendList(memberId));
    }
}
