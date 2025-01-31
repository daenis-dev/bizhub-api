package com.greenpalmsolutions.security.backups.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BackupRepository extends JpaRepository<Backup, Long> {
}
