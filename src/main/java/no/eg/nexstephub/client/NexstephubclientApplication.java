package no.eg.nexstephub.client;

import no.eg.nexstephub.client.domain.Client;
import no.eg.nexstephub.client.model.MessageDto;
import no.eg.nexstephub.client.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class NexstephubclientApplication {
    private static final Logger logger = LoggerFactory.getLogger(NexstephubclientApplication.class);

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    protected String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    protected String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.authorization-grant-type}")
    protected String googleGrantType;

    @Value("${access_token_url}")
    protected String accessTokenUrl;

    @Value("${audience_url}")
    protected String audienceUrl;

    @Value("${authorization_code_url}")
    protected String authorizationCodeUrl;

    @Value("${security_vendor}")
    protected Client.ClientTypeEnum securityVendor;

    public static void main(String[] args) {
        try {

            ConfigurableApplicationContext context = SpringApplication.run(NexstephubclientApplication.class, args);
            context.start();
            // NexstephubclientApplication nexstephubclientApplication = (NexstephubclientApplication) context.getBean("nexstephubclientApplication");
            MessageService messageService = (MessageService) context.getBean("messageService");
            MessageDto messageReceived = null;
            //messageReceived = messageService.getMessage();
            MessageDto messageSent = new MessageDto();
            messageSent.setMessage("Melding fra klient");
            messageReceived = messageService.postMessage(messageSent);
            logger.info(messageReceived.getMessage());
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
    }

    public NexstephubclientApplication() {

    }

    @Bean
    public Client client() {
        Client client = new Client(securityVendor);
        client.setClientId(googleClientId);
        client.setClientSecret(googleClientSecret);
        client.setGrantType(googleGrantType);
        client.setUrlForGetToken(accessTokenUrl);
        client.setUrlForAuthorizationCode(authorizationCodeUrl);
        client.setAudience(audienceUrl);
        return client;
    }

    @Bean
    public RestTemplate restTemplate(@Autowired RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}
