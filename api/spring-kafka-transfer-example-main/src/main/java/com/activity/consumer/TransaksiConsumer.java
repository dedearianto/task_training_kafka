package com.activity.consumer;


import com.activity.dto.TrxDto;
import com.activity.entity.Akun;
import com.activity.entity.Transaksi;
import com.activity.repository.AkunRepository;
import com.activity.repository.TransaksiRepository;
import com.activity.service.AkunService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TransaksiConsumer {

    private final AkunService akunService;
    private final TransaksiRepository transaksiRepository;

    private final AkunRepository akunRepository;

    public TransaksiConsumer(AkunService akunService, TransaksiRepository transaksiRepository, AkunRepository akunRepository) {

        this.akunService = akunService;
        this.transaksiRepository = transaksiRepository;
        this.akunRepository = akunRepository;
    }

    @KafkaListener(topics = "${topic.trx}")
    @Transactional
    public void handleTransaksi(TrxDto trxDto){
        log.info("Menerima data transaksi: {}", trxDto);
        Transaksi entity = akunService.findByIdTransaksiNew(trxDto.getId());
        Boolean errorHappen = false;
        if(null!=entity){
            Akun akunPenerima = akunRepository.findByNoRek(entity.getNorekPenerima());
            if(null == akunPenerima){
                entity.setStatus("E");//Error
                errorHappen = true;
            }

            Akun akunSumber = akunRepository.findByNoRek(entity.getNorekPengirim());
            if(null == akunSumber){
                entity.setStatus("E");//Error
                errorHappen = true;
            }
            if(!errorHappen){
                entity.setStatus("F");//selesai
            }
            entity = transaksiRepository.save(entity);
            log.info("data transaksi berhasil: {}", entity);
        }

    }

}
