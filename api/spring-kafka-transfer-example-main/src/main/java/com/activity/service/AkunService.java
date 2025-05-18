package com.activity.service;

import com.activity.dto.AkunDto;
import com.activity.dto.TrxDto;
import com.activity.entity.Akun;
import com.activity.entity.Transaksi;

import java.util.List;

public interface AkunService {

    Akun saveDataAkun(AkunDto akun);
    List<Akun> findAllData();

    TrxDto transfer(TrxDto trxDto);

    Transaksi findByIdTransaksiNew(Long id);

    List<Transaksi> findAllTransaksi();

}
