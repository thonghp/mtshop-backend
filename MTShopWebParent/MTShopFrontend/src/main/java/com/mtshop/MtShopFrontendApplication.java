package com.mtshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.mtshop.common.entity"})
public class MtShopFrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MtShopFrontendApplication.class, args);
    }

}
