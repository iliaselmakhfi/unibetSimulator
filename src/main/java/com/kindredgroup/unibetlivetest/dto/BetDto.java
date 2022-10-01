package com.kindredgroup.unibetlivetest.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class BetDto {

    private String selectionId;
    private BigDecimal cote;
    private BigDecimal mise;
}
