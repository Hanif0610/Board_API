package com.api_board;

import com.api_board.util.FileUploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileUploadProperties.class
})
public class ApiBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiBoardApplication.class, args);
    }

}
