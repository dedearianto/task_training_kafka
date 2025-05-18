package com.activity.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.listener.CommonErrorHandler;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.TopicPartitionOffset;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class KafkaErrorHandler implements CommonErrorHandler {

    @Override
    public boolean handleOne(Exception thrownException, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
        System.err.println("Terjadi kesalahan saat memproses record: " + record + ", Exception: " + thrownException.getMessage());
        thrownException.printStackTrace();
        // Implementasi penanganan untuk satu record yang gagal
        // Return true jika error dianggap sudah ditangani, false jika container perlu mencoba kembali
        return true;
    }

    @Override
    public void handleRemaining(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer, MessageListenerContainer container) {
        System.err.println("Terjadi kesalahan saat memproses. Record yang gagal dan sisanya: " + records + ", Exception: " + thrownException.getMessage());
        thrownException.printStackTrace();
        // Implementasi penanganan untuk record yang gagal dan record setelahnya
    }

    @Override
    public void handleBatch(Exception thrownException, ConsumerRecords<?, ?> data, Consumer<?, ?> consumer, MessageListenerContainer container, Runnable invokeListener) {
        System.err.println("Terjadi kesalahan saat memproses batch: " + data.count() + " records, Exception: " + thrownException.getMessage());
        thrownException.printStackTrace();
        // Implementasi penanganan untuk batch record yang gagal
        // Biasanya di sini Anda akan melakukan seek jika ingin memproses ulang batch
    }

    @Override
    public <K, V> ConsumerRecords<K, V> handleBatchAndReturnRemaining(Exception thrownException, ConsumerRecords<?, ?> data, Consumer<?, ?> consumer, MessageListenerContainer container, Runnable invokeListener) {
        System.err.println("Terjadi kesalahan saat memproses batch dan mengembalikan sisa: " + data.count() + " records, Exception: " + thrownException.getMessage());
        thrownException.printStackTrace();
        // Implementasi penanganan untuk batch record yang gagal dan mengembalikan subset untuk diproses ulang
        return ConsumerRecords.empty(); // Contoh: tidak ada yang diproses ulang
    }

    @Override
    public int deliveryAttempt(TopicPartitionOffset topicPartitionOffset) {
        // Implementasi untuk mendapatkan informasi delivery attempt (jika diaktifkan)
        return 0;
    }

    @Override
    public void clearThreadState() {
        // Implementasi untuk membersihkan thread state (jika diperlukan)
    }

    @Override
    public boolean isAckAfterHandle() {
        return true; // Secara default, commit offset setelah penanganan error tanpa exception
    }

    @Override
    public void setAckAfterHandle(boolean ack) {
        // Implementasi untuk mengontrol apakah offset di-commit setelah penanganan error
        // Perhatikan bahwa default implementation melempar UnsupportedOperationException
        System.out.println("Mengatur ackAfterHandle menjadi: " + ack);
    }

    @Override
    public void handleOtherException(Exception thrownException, Consumer<?, ?> consumer, MessageListenerContainer container, boolean batchListener) {
        log.error("Error in process with Exception {} and the consumer is {}", thrownException, consumer);
    }
}