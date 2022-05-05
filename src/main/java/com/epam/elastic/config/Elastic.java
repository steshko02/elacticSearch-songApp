package com.epam.elastic.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
    @EnableElasticsearchRepositories(basePackages = "com.epam.elastic.repository")
    @ComponentScan(basePackages = "com.epam.elastic")
    class Elastic extends AbstractElasticsearchConfiguration {

        @Value("${elasticsearch.url}")
        public String elasticsearchUrl;



        @Bean
        @Override
        public RestHighLevelClient elasticsearchClient() {
            final ClientConfiguration config = ClientConfiguration.builder()
                    .connectedTo(elasticsearchUrl)
                    .build();

            return RestClients.create(config).rest();
        }

        @Bean
        public ElasticsearchRestTemplate elasticsearchTempl() {
            return new ElasticsearchRestTemplate(elasticsearchClient());
        }
    }
