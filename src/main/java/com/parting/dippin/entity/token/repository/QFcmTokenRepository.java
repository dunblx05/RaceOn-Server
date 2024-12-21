package com.parting.dippin.entity.token.repository;

import java.util.List;

public interface QFcmTokenRepository {

    public List<String> findTokensByMemberId(int memberId);
}
