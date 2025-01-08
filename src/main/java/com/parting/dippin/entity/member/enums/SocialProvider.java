package com.parting.dippin.entity.member.enums;

public enum SocialProvider {
    KAKAO("https://kauth.kakao.com"),
    APPLE("https://appleid.apple.com"),
    GOOGLE("https://oauth2.googleapis.com");

    private final String issuer;

    SocialProvider(String issuer) {
        this.issuer = issuer;
    }

    public String getIssuer() {
        return issuer;
    }
}
