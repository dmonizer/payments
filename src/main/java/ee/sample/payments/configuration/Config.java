package ee.sample.payments.configuration;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableJpaAuditing
@EnableWebMvc
public class Config {
    @Bean
    AsyncHttpClient asyncHttpClient() {
        var clientBuilder = Dsl.config()
                .setConnectTimeout(5000);
        return Dsl.asyncHttpClient(clientBuilder);
    }
}
