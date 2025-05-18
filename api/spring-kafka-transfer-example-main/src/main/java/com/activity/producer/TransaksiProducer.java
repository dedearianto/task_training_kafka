package com.activity.producer;

import com.activity.dto.TrxDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class TransaksiProducer {

    @Value("${topic.trx}")
    private String trxTopic;

    private final KafkaTemplate<String, TrxDto> kafkaTemplate;

    public TransaksiProducer(KafkaTemplate<String, TrxDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTrx(TrxDto trx) {
        String key = UUID.randomUUID().toString();
        CompletableFuture<SendResult<String, TrxDto>> future =
                kafkaTemplate.send(trxTopic, key, trx);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Transaksi berhasil dikirim! Trs Referensi: {}, Offset: {}",
                        trx.getNoReferensi(),
                        result.getRecordMetadata().offset());
            } else {
                log.error("Gagal mengirim data tranaksi: {}", ex.getMessage());
                throw new RuntimeException("Error mengirim penjualan ke Kafka", ex);
            }
        });
    }
}
