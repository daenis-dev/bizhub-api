package com.greenpalmsolutions.security.artifacts.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface ArtifactRepository extends JpaRepository<Artifact, Long> {

    Optional<Artifact> findByIdAndUserId(Long id, String userId);

    @Query("SELECT COUNT(a) > 50 FROM Artifact a WHERE a.userId = :userId")
    boolean maximumArtifactsExistForUserId(@Param("userId") String userId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Artifact a " +
            "WHERE a.filePath = :filePath " +
            "AND a.userId = :userId " +
            "AND a.hash <> :hash ")
    boolean artifactIsModifiedWithFilePathAndUserIdAndHash(@Param("filePath") String filePath,
                                                           @Param("userId") String userId,
                                                           @Param("hash") byte[] hash);

    List<Artifact> findByUserId(String userId);
}
