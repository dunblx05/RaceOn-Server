package com.parting.dippin.domain.member.service;

import static com.parting.dippin.core.common.file.BucketPath.PROFILE_IMAGE_BUCKET_PATH;

import com.parting.dippin.api.member.dto.PatchProfileImageResDto;
import com.parting.dippin.core.common.file.PresignedUrlGenerator;
import com.parting.dippin.entity.member.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileUpdateService {

    private final PresignedUrlGenerator presignedUrlGenerator;
    private final MemberReader memberReader;

    public PatchProfileImageResDto updateProfileImage() {
        return presignedUrlGenerator.generatePreSignedUrl(PROFILE_IMAGE_BUCKET_PATH);
    }

    @Transactional
    public void updateProfile(
            int memberId,
            String newProfileUrl,
            String newNickname
    ) {
        MemberEntity memberEntity = memberReader.getMemberById(memberId);

        memberEntity.updateProfile(newProfileUrl, newNickname);
    }
}
