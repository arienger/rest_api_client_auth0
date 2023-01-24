package no.eg.nexstephub.client;

import no.eg.nexstephub.client.domain.Client;
import no.eg.nexstephub.client.model.ByggOfficeDto;
import no.eg.nexstephub.client.model.ByggOfficeTransactionDto;
import no.eg.nexstephub.client.model.MessageDto;
import no.eg.nexstephub.client.service.ByggOfficeService;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

            ByggOfficeService byggOfficeService = (ByggOfficeService) context.getBean("byggOfficeService");
            ByggOfficeDto byggOfficeDto = createByggOfficeDto();
            messageReceived = byggOfficeService.export(byggOfficeDto);
            logger.info("byggOffice: " + messageReceived);
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
    }

    private static ByggOfficeDto createByggOfficeDto() {
        ByggOfficeDto byggOfficeDto = new ByggOfficeDto();
        byggOfficeDto.setClientId("clientId");
        List<ByggOfficeTransactionDto> byggOfficeTransactionDtoList = new ArrayList<>();
        ByggOfficeTransactionDto byggOfficeTransactionDto = new ByggOfficeTransactionDto();
        byggOfficeTransactionDto.setAmount(1.00);
        byggOfficeTransactionDto.setAmountType(0);
        byggOfficeTransactionDto.setArticleNo("art1");
        byggOfficeTransactionDto.setComment("comment");
        byggOfficeTransactionDto.setArticleType(0);
        byggOfficeTransactionDto.setClientNo("cli1");
        byggOfficeTransactionDto.setCrmId(0);
        byggOfficeTransactionDto.setImportId("import1");
        byggOfficeTransactionDto.setPeriod(1);
        byggOfficeTransactionDto.setPpkType(0);
        byggOfficeTransactionDto.setProject("prosjekt1");
        byggOfficeTransactionDto.setQuantity(1);
        byggOfficeTransactionDto.setDate(Instant.now());
        byggOfficeTransactionDto.setReference("ref1");
        byggOfficeTransactionDto.setSource(10);
        byggOfficeTransactionDto.setOrgNo("111111111");
        byggOfficeTransactionDto.setStatus(0);
        byggOfficeTransactionDto.setGeneralLedgerAccount("1234");
        byggOfficeTransactionDto.setProductionCode("prodkode1");
        byggOfficeTransactionDto.setStatusDate(Instant.now());
        byggOfficeTransactionDto.setStatusText("statustext1");
        byggOfficeTransactionDto.setVendorNo(2);
        byggOfficeTransactionDto.setVoucherNo(1);
        byggOfficeTransactionDto.setVoucherNoSequence(2);
        byggOfficeTransactionDto.setVoucherNoAsString("2S");
        byggOfficeTransactionDto.setVendorName("vend name");
        byggOfficeTransactionDto.setVoucherNoSplitNo(0);
        byggOfficeTransactionDto.setWebId("webId 1");
        byggOfficeTransactionDto.setReserved1("reserved 1");

        byggOfficeTransactionDtoList.add(byggOfficeTransactionDto);

        ByggOfficeTransactionDto byggOfficeTransactionDto2 = new ByggOfficeTransactionDto();
        byggOfficeTransactionDto2.setAmount(2.00);
        byggOfficeTransactionDto2.setAmountType(0);
        byggOfficeTransactionDto2.setArticleNo("art2");
        byggOfficeTransactionDto2.setComment("comment");
        byggOfficeTransactionDto2.setArticleType(0);
        byggOfficeTransactionDto2.setClientNo("cli2");
        byggOfficeTransactionDto2.setCrmId(0);
        byggOfficeTransactionDto2.setImportId("import1");
        byggOfficeTransactionDto2.setPeriod(1);
        byggOfficeTransactionDto2.setPpkType(0);
        byggOfficeTransactionDto2.setProject("prosjekt1");
        byggOfficeTransactionDto2.setQuantity(1);
        byggOfficeTransactionDto2.setDate(Instant.now());
        byggOfficeTransactionDto2.setReference("ref1");
        byggOfficeTransactionDto2.setSource(10);
        byggOfficeTransactionDto2.setOrgNo("111111111");
        byggOfficeTransactionDto2.setStatus(0);
        byggOfficeTransactionDto2.setGeneralLedgerAccount("1234");
        byggOfficeTransactionDto2.setProductionCode("prodkode1");
        byggOfficeTransactionDto2.setStatusDate(Instant.now());
        byggOfficeTransactionDto2.setStatusText("statustext1");
        byggOfficeTransactionDto2.setVendorNo(2);
        byggOfficeTransactionDto2.setVoucherNo(1);
        byggOfficeTransactionDto2.setVoucherNoSequence(2);
        byggOfficeTransactionDto2.setVoucherNoAsString("2S");
        byggOfficeTransactionDto2.setVendorName("vend name");
        byggOfficeTransactionDto2.setVoucherNoSplitNo(0);
        byggOfficeTransactionDto2.setWebId("webId 1");
        byggOfficeTransactionDto2.setReserved1("reserved 1");

        byggOfficeTransactionDtoList.add(byggOfficeTransactionDto2);


        byggOfficeDto.setTransactions(byggOfficeTransactionDtoList);

        return byggOfficeDto;
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
