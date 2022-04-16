package org.example.store.config;


import org.example.store.pay.PayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@Configuration
public class AppConfig {
    @Value("${pay.id}")
    private Integer merchantId;
    @Value("${pay.public-key}")
    private String publicKey;
    @Value("${pay.private-key}")
    private String privateKey;

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PayClient getPayClient() {
        return PayClient.getClient(
                merchantId, publicKey, privateKey
        );
    }
}
