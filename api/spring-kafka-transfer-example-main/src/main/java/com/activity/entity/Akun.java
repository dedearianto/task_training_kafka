package com.activity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Table(name = "master_akun")
@Data
public class Akun {

    @Id
    @Column(name = "nomor_rekening", nullable = false)
    String noRek;

    @ColumnDefault("0")
    @Column(name = "saldo")
    BigDecimal saldo;

    @Column(name = "tipe_akun")
    String tipeAkun;
}
