package com.sbstu.client.service.impl;

import com.sbstu.client.model.BlockchainType;
import com.sbstu.client.model.ListenerConnectionParams;
import com.sbstu.client.service.ClientToListenerIntegrationService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientToListenerIntegrationServiceImpl implements ClientToListenerIntegrationService {

    public static final String URL = "http://localhost:8091/";

    @Override
    public String getBlock(BlockchainType blockchainType, String hash) {
        Map<String, String> params = new HashMap<>();
        params.put("hash", hash);
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(URL + blockchainType.toString().toLowerCase() + "/block/getByHash")
                .queryParam("hash", "{hash}")
                .encode()
                .toUriString();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(null, null), String.class, params).getBody();
    }

    @Override
    public String getBlock(BlockchainType blockchainType, Long number) {
        Map<String, String> params = new HashMap<>();
        params.put("number", number.toString());
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(URL + blockchainType.toString().toLowerCase() + "/block/getByNumber")
                .queryParam("number", "{number}")
                .encode()
                .toUriString();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(null, null), String.class, params).getBody();
    }

    @Override
    public String getLatestBlock(BlockchainType blockchainType) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(URL + blockchainType.toString().toLowerCase() + "/block/latest")
                .encode()
                .toUriString();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(null, null), String.class, Collections.emptyMap()).getBody();
    }

    @Override
    public String getTransaction(BlockchainType blockchainType, String hash) {
        Map<String, String> params = new HashMap<>();
        params.put("hash", hash);
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(URL + blockchainType.toString().toLowerCase() + "/transaction/getByHash")
                .queryParam("hash", "{hash}")
                .encode()
                .toUriString();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(null, null), String.class, params).getBody();    }

    @Override
    public String getLog(BlockchainType blockchainType, String transactionHash) {
        throw new UnsupportedOperationException();
    }
}
