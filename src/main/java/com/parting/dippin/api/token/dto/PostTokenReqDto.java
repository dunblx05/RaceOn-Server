package com.parting.dippin.api.token.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 없을 경우 테스트 시 HttpMessageNotReadableException 에러 발생
public class PostTokenReqDto {

    String token;

    @Builder
    private PostTokenReqDto(String token) {
        this.token = token;
    }
}
