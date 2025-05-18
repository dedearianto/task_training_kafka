package com.activity.service.impl;

import com.activity.dto.AkunDto;
import com.activity.dto.TrxDto;
import com.activity.entity.Akun;
import com.activity.entity.Transaksi;
import com.activity.producer.TransaksiProducer;
import com.activity.repository.AkunRepository;
import com.activity.repository.TransaksiRepository;
import com.activity.service.AkunService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AkunServiceImpl implements AkunService {

    private final AkunRepository akunRepository;
    private  final TransaksiProducer transaksiProducer;
    private final TransaksiRepository transaksiRepository;

    public AkunServiceImpl(AkunRepository akunRepository, TransaksiProducer transaksiProducer, TransaksiRepository transaksiRepository) {
        this.akunRepository = akunRepository;
        this.transaksiProducer = transaksiProducer;
        this.transaksiRepository = transaksiRepository;
    }

    @Override
    public Akun saveDataAkun(AkunDto akun) {
        Akun entity = dtoToEntity(akun);
        return akunRepository.save(entity);
    }

    @Override
    public List<Akun> findAllData() {
        return akunRepository.findAll();
    }

    @Override
    public TrxDto transfer(TrxDto trxDto) {
        trxDto.setNoReferensi(generateReferensi());
        Transaksi entity = dtoToTrxEntity(trxDto);
        entity = transaksiRepository.save(entity);
        TrxDto trx = trxEntityToDto(entity);
        transaksiProducer.sendTrx(trx);
        return trx;
    }

    @Override
    public Transaksi findByIdTransaksiNew(Long id) {
        return transaksiRepository.findById(id).orElse(null);
    }

    @Override
    public List<Transaksi> findAllTransaksi() {
        return transaksiRepository.findAll();
    }

    private String generateReferensi() {
        return "FT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private TrxDto trxEntityToDto(Transaksi entity){
        TrxDto trxDto = new TrxDto();
        trxDto.setId(entity.getId());
        trxDto.setNoReferensi(entity.getNoRef());
        trxDto.setNorekPenerima(entity.getNorekPenerima());
        trxDto.setNorekPengirim(entity.getNorekPengirim());
        trxDto.setJumlahTransfer(Double.valueOf(String.valueOf(entity.getJumlahTransfer())));
        trxDto.setStatus(entity.getStatus());
        return trxDto;
    }
    private Transaksi dtoToTrxEntity(TrxDto trxDto){
        Transaksi transaksi = new Transaksi();
        transaksi.setNoRef(String.valueOf(trxDto.getNoReferensi()));
        transaksi.setNorekPengirim(String.valueOf(trxDto.getNorekPengirim()));
        transaksi.setNorekPenerima(String.valueOf(trxDto.getNorekPenerima()));
        transaksi.setJumlahTransfer(BigDecimal.valueOf(trxDto.getJumlahTransfer()));
        transaksi.setStatus(String.valueOf("P"));//proses
        return  transaksi;
    }
    private Akun dtoToEntity(AkunDto akunDto){
        Akun akun = new Akun();
        akun.setNoRek(akunDto.getNorek());
        akun.setSaldo(akunDto.getSaldo());
        akun.setTipeAkun(akunDto.getTipeAkun());
        return akun;
    }
}
