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
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            int index = RandomGenerator.getDefault().nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        long timestamp = Instant.now().toEpochMilli();
        return sb.append("_").append(timestamp).toString();
    }

    @Override
    public ScheduleKeyDetails findScheduleKeyForCurrentUser() {
        return scheduleKeyRepository.findActiveScheduleKeyForUserId(findCurrentAccount.getUserIdForCurrentAccount());
    }

    @Override
    public void disableActiveScheduleKeyForUser() {
        scheduleKeyRepository.disableAllScheduleKeysForUser(findCurrentAccount.getUserIdForCurrentAccount());
    }

    @Override
    public boolean scheduleKeyIsValidForUser(String scheduleKey, String userId) {
        return scheduleKeyRepository.scheduleKeyIsValidForUserId(scheduleKey, userId);
    }
}
