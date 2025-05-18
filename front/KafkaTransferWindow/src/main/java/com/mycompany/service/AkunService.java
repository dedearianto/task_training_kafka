/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author dede
 */
public class AkunService implements AkunInterface{

    @Override
    public void saveAkun(String norek, BigDecimal saldo, String tipeAkun) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
			"http://localhost:8181/akun/saveAkun");
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("{");
        strBuild.append("\"norek\":");
        strBuild.append("\"").append(norek).append("\"").append(",");
        strBuild.append("\"saldo\":");
        strBuild.append(saldo).append(",");
        strBuild.append("\"tipeAkun\":");
        strBuild.append("\"").append(tipeAkun).append("\"")
                .append("}");
        StringEntity input;
        try {
            input = new StringEntity(strBuild.toString());
            input.setContentType("application/json");
	    postRequest.setEntity(input);
                HttpResponse response = httpClient.execute(postRequest);
                if (response.getStatusLine().getStatusCode() != 201 || response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatusLine().getStatusCode());
		}
                httpClient.getConnectionManager().shutdown();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AkunService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AkunService.class.getName()).log(Level.SEVERE, null, ex);
        }
		

		
    }

    @Override
    public List<String> getAllAkun() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(
			"http://localhost:8181/akun/getAll");
		getRequest.addHeader("accept", "application/json");

		HttpResponse response;
                List<String> strList = new ArrayList<>();
        try {
            response = httpClient.execute(getRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}

		BufferedReader br = new BufferedReader(
                         new InputStreamReader((response.getEntity().getContent())));
                
                String output;		
		while ((output = br.readLine()) != null) {
			strList.add(output);
		}

		httpClient.getConnectionManager().shutdown();

        } catch (IOException ex) {
            Logger.getLogger(AkunService.class.getName()).log(Level.SEVERE, null, ex);
        }

		
		return strList;
    }

    @Override
    public void transfer(String norekSumber, String norekTujuan, Double jumlah) {
         DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
			"http://localhost:8181/akun/transfer");
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("{");
        strBuild.append("\"norekPengirim\":");
        strBuild.append("\"").append(norekSumber).append("\"").append(",");
        strBuild.append("\"jumlahTransfer\":");
        strBuild.append(jumlah).append(",");
        strBuild.append("\"norekPenerima\":");
        strBuild.append("\"").append(norekTujuan).append("\"")
                .append("}");
        StringEntity input;
        try {
            input = new StringEntity(strBuild.toString());
            input.setContentType("application/json");
	    postRequest.setEntity(input);
                HttpResponse response = httpClient.execute(postRequest);
                if (response.getStatusLine().getStatusCode() != 201 || response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatusLine().getStatusCode());
		}
                httpClient.getConnectionManager().shutdown();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AkunService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AkunService.class.getName()).log(Level.SEVERE, null, ex);
        }
		
    }

    @Override
    public List<String> getAllTransaksi() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(
			"http://localhost:8181/akun/getAllTransaksi");
		getRequest.addHeader("accept", "application/json");

		HttpResponse response;
                List<String> strList = new ArrayList<>();
        try {
            response = httpClient.execute(getRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}

		BufferedReader br = new BufferedReader(
                         new InputStreamReader((response.getEntity().getContent())));
                
                String output;		
		while ((output = br.readLine()) != null) {
			strList.add(output);
		}

		httpClient.getConnectionManager().shutdown();

        } catch (IOException ex) {
            Logger.getLogger(AkunService.class.getName()).log(Level.SEVERE, null, ex);
        }

		
		return strList;
    }
    
}
