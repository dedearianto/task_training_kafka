package com.activity.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AkunDto {

    String norek;
    BigDecimal saldo;
    String tipeAkun;
}
