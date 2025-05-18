package com.activity.repository;

import com.activity.entity.Akun;
import com.activity.entity.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, Long> {

}

