package com.kindredgroup.unibetlivetest.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BetDto {

    private String selectionId;
    private BigDecimal cote;
    private BigDecimal mise;
}
