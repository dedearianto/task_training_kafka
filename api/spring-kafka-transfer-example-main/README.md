# Spring Boot Kafka Avro Project

## Deskripsi
Proyek ini adalah implementasi Spring Boot dengan Apache Kafka menggunakan Avro schema untuk serialisasi data. Proyek ini mendemonstrasikan penggunaan Kafka dengan format data Avro untuk sistem penjualan.

## Prasyarat
- Java 21 atau yang lebih baru
- Docker dan Docker Compose
- Maven
- PostgreSQL

## Struktur Proyek
```plaintext
spring-kafka-avro/
+-- src/
¦   +-- main/
¦   ¦   +-- java/
¦   ¦   ¦   +-- com/
¦   ¦   ¦       +-- activity/
¦   ¦   ¦           +-- controller/
¦   ¦   ¦           +-- dto/
¦   ¦   ¦           +-- service/
¦   ¦   ¦           +-- exception/
¦   ¦   +-- resources/
¦   ¦       +-- avro/
¦   ¦       ¦   +-- Sale.avsc
¦   ¦       +-- application.yml
+-- docker-compose.yml
+-- README.md
```

## Langkah-langkah Instalasi

### 1. Clone Repository
```bash
git clone <repository-url>
cd spring-kafka-avro
```

### 2. Konfigurasi Database
Jalankan perintah SQL berikut untuk membuat database dan tabel:

```sql
-- Buat database
CREATE DATABASE sales_db;

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sales (
    id SERIAL PRIMARY KEY,
    invoice_number VARCHAR(50) UNIQUE NOT NULL,
    sale_date TIMESTAMP NOT NULL,
    total_amount DECIMAL(12,2) NOT NULL,
    payment_status VARCHAR(20) NOT NULL,
    customer_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sale_details (
    id SERIAL PRIMARY KEY,
    sale_id INTEGER REFERENCES sales(id),
    product_id INTEGER REFERENCES products(id),
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL
);

CREATE TABLE financial_records (
    id SERIAL PRIMARY KEY,
    sale_id INTEGER REFERENCES sales(id),
    transaction_date TIMESTAMP NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    payment_method VARCHAR(50),
    notes TEXT
);


INSERT INTO products
(id, "name", description, price, stock_quantity, created_at, updated_at)
VALUES(nextval('products_id_seq'::regclass), 'Kaos Polo Ukuran M', 'Kaos Polo Ukuran M', 90000, 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

```

### 3. Konfigurasi Docker
Jalankan services menggunakan Docker Compose:

```bash
docker-compose up -d
```

### 4. Build dan Jalankan Aplikasi
```bash
mvn clean install
mvn spring-boot:run
```

## Konfigurasi Aplikasi
File `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/sales_db
    username: postgres
    password: postgres
  kafka:
    bootstrap-servers: localhost:9092
    properties:
      schema.registry.url: http://localhost:8081
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: io.confluent.kafka.serializers.KafkaAvroSerializer
    consumer:
      group-id: sale-group
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: io.confluent.kafka.serializers.KafkaAvroDeserializer

topic:
  name:
    sale: sale-topic
```

## API Endpoints

### Create Sale
```bash
curl -X POST http://localhost:8080/api/sale \
-H "Content-Type: application/json" \
-d '{
  "invoiceNumber": "INV-001",
  "saleDate": "2024-01-20T10:00:00",
  "totalAmount": 1000000,
  "paymentStatus": "PAID",
  "customerName": "John Doe",
  "saleDetails": [
    {
      "productId": 1,
      "quantity": 2,
      "unitPrice": 500000
    }
  ]
}'
```


## Monitoring
- Schema Registry UI: http://localhost:8081
- Kafka Topics: http://localhost:9092

## Troubleshooting

### Masalah Umum dan Solusi
1. **Connection refused ke Kafka**
   - Pastikan container Kafka berjalan: `docker ps`
   - Periksa logs: `docker-compose logs kafka`

2. **Schema Registry Error**
   - Pastikan Schema Registry berjalan
   - Periksa compatibility mode

3. **Database Connection Issues**
   - Verifikasi kredensial database
   - Pastikan database PostgreSQL berjalan

### Perintah Docker yang Berguna
```bash
# Lihat status container
docker-compose ps

# Lihat logs
docker-compose logs -f

# Restart services
docker-compose restart

# Stop services
docker-compose down

# Start services
docker-compose up -d
```

## Catatan Penting
- Backup database secara berkala
- Monitor penggunaan disk untuk logs Kafka
- Periksa Schema Registry untuk kompatibilitas schema        
