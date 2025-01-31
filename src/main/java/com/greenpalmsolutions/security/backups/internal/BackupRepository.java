package com.greenpalmsolutions.security.backups.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface BackupRepository extends JpaRepository<Backup, Long> {

    Optional<Backup> findByFilePathAndUserId(String filePath, String userId);
}
