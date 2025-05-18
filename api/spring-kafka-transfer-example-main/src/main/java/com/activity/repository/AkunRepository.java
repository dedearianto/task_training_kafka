package com.activity.repository;

import com.activity.entity.Akun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AkunRepository extends JpaRepository<Akun, String> {
    Akun findByNoRek(String norek);
}

