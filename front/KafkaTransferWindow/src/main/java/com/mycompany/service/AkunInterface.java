/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.service;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author dede
 */
public interface AkunInterface {
    
    void saveAkun(String norek, BigDecimal saldo, String tipeAkun);
    List<String> getAllAkun();
    void transfer(String norekSumber, String norekTujuan, Double jumlah);
    List<String> getAllTransaksi();
    
}
