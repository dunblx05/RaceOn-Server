package com.parting.dippin.api.member.dto;

import java.net.URL;

public class PatchProfileImageResDto {

    private final URL preSignedUrl;

    public PatchProfileImageResDto(URL preSignedUrl) {
        this.preSignedUrl = preSignedUrl;
    }

    public String getPreSignedUrl() {
        return preSignedUrl.toString();
    }

    public String getContentUrl() {
        return String.format(
                "%s://%s%s",
                preSignedUrl.getProtocol(),
                preSignedUrl.getAuthority(),
                preSignedUrl.getPath()
        );
    }
}
