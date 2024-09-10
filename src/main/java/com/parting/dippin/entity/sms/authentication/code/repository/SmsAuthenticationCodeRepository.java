package com.parting.dippin.entity.sms.authentication.code.repository;

import com.parting.dippin.entity.sms.authentication.code.SmsAuthenticationCode;
import com.parting.dippin.entity.sms.authentication.code.SmsAuthenticationCodeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsAuthenticationCodeRepository extends JpaRepository<SmsAuthenticationCode, SmsAuthenticationCodeId>, QSmsAuthenticationCodeRepository {

}
