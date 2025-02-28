package com.greenpalmsolutions.security.schedulekeys.internal;

import com.greenpalmsolutions.security.accounts.api.behavior.FindCurrentAccount;
import com.greenpalmsolutions.security.accounts.api.behavior.FindUserIdForUsername;
import com.greenpalmsolutions.security.schedulekeys.api.behavior.CreateScheduleKey;
import com.greenpalmsolutions.security.schedulekeys.api.behavior.DisableScheduleKey;
import com.greenpalmsolutions.security.schedulekeys.api.behavior.FindScheduleKey;
import com.greenpalmsolutions.security.schedulekeys.api.behavior.ScheduleKeyIsValidForUser;
import com.greenpalmsolutions.security.schedulekeys.api.model.ScheduleKeyDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

import java.util.random.RandomGenerator;

@Service
@RequiredArgsConstructor
class ScheduleKeyService implements CreateScheduleKey, FindScheduleKey, DisableScheduleKey, ScheduleKeyIsValidForUser {

    private final ScheduleKeyRepository scheduleKeyRepository;
    private final FindCurrentAccount findCurrentAccount;

    @Transactional
    @Override
    public ScheduleKeyDetails createScheduleKeyForCurrentUser() {
        final String CURRENT_USER_ID = findCurrentAccount.getUserIdForCurrentAccount();
        scheduleKeyRepository.disableAllScheduleKeysForUser(CURRENT_USER_ID);
        return scheduleKeyRepository.save(new ScheduleKey().withUserIdAndToken(CURRENT_USER_ID, generateToken()))
                .getDetails();
    }

    private String generateToken() {
        long timestamp = Instant.now().toEpochMilli();
        return "schedule_" + timestamp;
    }

    @Override
    public ScheduleKeyDetails findScheduleKeyForCurrentUser() {
        return scheduleKeyRepository.findActiveScheduleKeyForUserId(findCurrentAccount.getUserIdForCurrentAccount());
    }

    @Transactional
    @Override
    public void disableActiveScheduleKeyForUser() {
        scheduleKeyRepository.disableAllScheduleKeysForUser(findCurrentAccount.getUserIdForCurrentAccount());
    }

    @Override
    public boolean scheduleKeyIsValidForUser(String scheduleKey, String userId) {
        return scheduleKeyRepository.scheduleKeyIsValidForUserId(scheduleKey, userId);
    }
}
