package com.greenpalmsolutions.security.schedulekeys.internal;

import com.greenpalmsolutions.security.schedulekeys.api.model.ScheduleKeyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface ScheduleKeyRepository extends JpaRepository<ScheduleKey, Long> {

    @Modifying
    @Query("UPDATE ScheduleKey sk SET sk.isActive = false WHERE sk.userId = :userId")
    void disableAllScheduleKeysForUser(@Param("userId") String userId);

    @Query("SELECT new com.greenpalmsolutions.security.schedulekeys.api.model.ScheduleKeyDetails(sk.id, sk.token) FROM ScheduleKey sk WHERE sk.userId = :userId AND sk.isActive = true")
    ScheduleKeyDetails findActiveScheduleKeyForUserId(@Param("userId") String userId);
}
