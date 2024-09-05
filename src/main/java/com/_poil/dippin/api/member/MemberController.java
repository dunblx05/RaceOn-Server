package com._poil.dippin.api.member;

import com._poil.dippin.domain.member.dao.IMemberDAO;
import com._poil.dippin.domain.member.dto.MemberDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("member")
@RestController()
public class MemberController {

    private final IMemberDAO memberDAO;

    @GetMapping()
    public List<MemberDto> find() {
        return this.memberDAO.find();
    }
}
