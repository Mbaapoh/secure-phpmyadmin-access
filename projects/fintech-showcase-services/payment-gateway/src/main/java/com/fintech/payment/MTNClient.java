package com.fintech.payment;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MTNClient {
    private final String baseUrl;

    public MTNClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String requestToPay(String amount, String currency, String externalId) throws Exception {
        String json = String.format(
            "{\"amount\":\"%s\", \"currency\":\"%s\", \"externalId\":\"%s\", \"payer\":{\"partyIdType\":\"MSISDN\", \"partyId\":\"237670000000\"}}",
            amount, currency, externalId
        );

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl + "/collection/v1_0/requesttopay"))
            .header("Content-Type", "application/json")
            .header("X-Reference-Id", java.util.UUID.randomUUID().toString())
            .header("X-Target-Environment", "sandbox")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return "Status: " + response.statusCode();
    }
}
