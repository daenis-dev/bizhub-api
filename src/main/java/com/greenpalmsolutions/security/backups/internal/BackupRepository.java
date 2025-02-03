package com.greenpalmsolutions.security.backups.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BackupRepository extends JpaRepository<Backup, Long> {

    @Query("SELECT SUM(b.fileSizeInBytes) FROM Backup b WHERE b.userId = :userId")
    long findStorageForUserInBytes(@Param("userId") String userId);

    List<Backup> findByUserId(String userId);
}
