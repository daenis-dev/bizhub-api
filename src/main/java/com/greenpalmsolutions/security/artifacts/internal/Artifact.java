package com.greenpalmsolutions.security.artifacts.internal;

import com.greenpalmsolutions.security.artifacts.api.model.ArtifactRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

@Entity
@Table(name = "artifacts")
@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@EqualsAndHashCode
public class Artifact {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artifacts_seq")
    @SequenceGenerator(name = "artifacts_seq", sequenceName = "artifacts_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "hash")
    private byte[] hash;

    @Column(name = "user_id")
    private String userId;

    Artifact mapFromCreateArtifactRequestAndUserId(ArtifactRequest request, String userId) {
        try {
            this.filePath = request.getFilePath();
            this.hash = Hex.decodeHex(request.getHash());
            this.userId = userId;
            return this;
        } catch (DecoderException ex) {
            throw new RuntimeException("An error occurred while creating the artifact", ex);
        }
    }
}
