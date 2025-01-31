package com.greenpalmsolutions.security.commontargetfiles.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CommonTargetFileRepository extends JpaRepository<CommonTargetFile, Long> {
}
