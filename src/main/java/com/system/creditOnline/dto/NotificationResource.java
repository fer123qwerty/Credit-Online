package com.system.creditOnline.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serial;
import java.time.OffsetDateTime;

/**
 * The type Notification resource.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationResource {

    /**
     * serialVersionUID .
     */
    @Serial
    private static final long serialVersionUID = 1453834849L;

    /**
     * code .
     */
    private String code;
    /**
     * message .
     */
    private String message;
    /**
     * timestamp .
     */
    private OffsetDateTime timestamp;
}
