package com.system.creditOnline.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Generic response.
 *
 * @param <T> the type parameter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class GenericResponse<T> {

    /**
     * data .
     */
    private T data;


    private NotificationResource notifications;

}
