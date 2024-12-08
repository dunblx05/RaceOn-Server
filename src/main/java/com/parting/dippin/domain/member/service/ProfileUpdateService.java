package com.parting.dippin.domain.member.service;

import static com.parting.dippin.core.common.file.BucketPath.PROFILE_IMAGE_BUCKET_PATH;

import com.parting.dippin.api.member.dto.PatchProfileImageResDto;
import com.parting.dippin.core.common.file.PresignedUrlGenerator;
import com.parting.dippin.core.exception.UserNotFoundException;
import com.parting.dippin.entity.member.MemberEntity;
import com.parting.dippin.entity.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileUpdateService {

    private final PresignedUrlGenerator presignedUrlGenerator;
    private final MemberRepository memberRepository;

    public PatchProfileImageResDto updateProfileImage() {
        return presignedUrlGenerator.generatePreSignedUrl(PROFILE_IMAGE_BUCKET_PATH);
    }

    @Transactional
    public void updateProfile(
            int memberId,
            String newProfileUrl,
            String newNickname
    ) {
        // todo 예외 처리 PR에서 수정할 예정.
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));

        memberEntity.updateProfile(newProfileUrl, newNickname);
    }
}
