package ee.sample.payments.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.ResourceReader;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@Configuration
public class TestConfiguration {

    @Value("classpath:payment.json")
    Resource resource;

    @Bean
    public String paymentJson() throws IOException {
        return Files.readString(ResourceUtils.getFile("classpath:payment.json").toPath());
    }
}
