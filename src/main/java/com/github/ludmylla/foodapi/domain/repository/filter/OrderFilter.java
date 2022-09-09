package com.github.ludmylla.foodapi.domain.repository.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Getter
@Setter
public class

OrderFilter {

    private Long userId;
    private Long restaurantId;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dateCreationStart;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dateCreationEnd;

}
