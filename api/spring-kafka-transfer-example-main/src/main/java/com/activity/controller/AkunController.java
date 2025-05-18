package com.activity.controller;

import com.activity.dto.AkunDto;
import com.activity.dto.ApiResponse;
import com.activity.dto.TrxDto;
import com.activity.entity.Akun;
import com.activity.entity.Transaksi;
import com.activity.service.AkunService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/akun")
public class AkunController {

    private final AkunService akunService;

    public AkunController(AkunService akunService) {
        this.akunService = akunService;
    }

    @PostMapping("/saveAkun")
    public ResponseEntity<ApiResponse> createSale(@RequestBody AkunDto akun) {
        Akun response = akunService.saveDataAkun(akun);
        return ResponseEntity.ok(new ApiResponse(
                true,
                "Success",
                response
        ));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAll() {
        List<Akun> response = akunService.findAllData();
        return ResponseEntity.ok(new ApiResponse(
                true,
                "Success",
                response
        ));
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse> transfer(@RequestBody TrxDto trxDto) {
        TrxDto response = akunService.transfer(trxDto);
        return ResponseEntity.ok(new ApiResponse(
                true,
                "Success",
                response
        ));
    }

    @GetMapping("/getAllTransaksi")
    public ResponseEntity<ApiResponse> getAllTransaksi() {
        List<Transaksi> response = akunService.findAllTransaksi();
        return ResponseEntity.ok(new ApiResponse(
                true,
                "Success",
                response
        ));
    }

}
