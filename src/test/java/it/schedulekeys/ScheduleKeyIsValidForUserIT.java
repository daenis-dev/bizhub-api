package it.schedulekeys;

import com.greenpalmsolutions.security.schedulekeys.api.behavior.ScheduleKeyIsValidForUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ScheduleKeyIsValidForUserIT {

    @Autowired
    private ScheduleKeyIsValidForUser scheduleKeyIsValidForUser;

    @Test
    void scheduleKeyIsValidForUser() {
        boolean theScheduleKeyIsValidForTheUser = scheduleKeyIsValidForUser.scheduleKeyIsValidForUser(
                "xyz", "456-def");

        assertThat(theScheduleKeyIsValidForTheUser).isTrue();
    }
}
