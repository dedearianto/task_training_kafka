package com.activity.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "master_transaksi")
@Data
public class Transaksi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nomor_referensi", nullable = false)
    String noRef;
    @Column(name = "norek_pengirim", nullable = false)
    String norekPengirim;
    @Column(name = "norek_penerima", nullable = false)
    String norekPenerima;
    @Column(name = "jumlah_transfer", nullable = false)
    BigDecimal jumlahTransfer;
    @Column(name = "status", nullable = false)
    String status;
}
