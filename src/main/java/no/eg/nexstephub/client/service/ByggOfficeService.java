package no.eg.nexstephub.client.service;

import no.eg.nexstephub.client.domain.BearerAuthenticationToken;
import no.eg.nexstephub.client.domain.Client;
import no.eg.nexstephub.client.model.ByggOfficeDto;
import no.eg.nexstephub.client.model.GeneralResponseDto;
import no.eg.nexstephub.client.model.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ByggOfficeService extends AbstractService{
    private static final Logger logger = LoggerFactory.getLogger(ByggOfficeService.class);


    public ByggOfficeService() {
    }

    public MessageDto export(ByggOfficeDto byggOfficeDto) {
        if (client.getClientType() == Client.ClientTypeEnum.AUTH0) {
            BearerAuthenticationToken bearerAuthenticationToken = authorizationService.getBearerAuthenticationToken();
            if (bearerAuthenticationToken != null && !bearerAuthenticationToken.isExpired()) {
                String url = "http://localhost:8080/byggoffice/export";
                // ResponseEntity<GeneralResponseDto> response = doPostRequest(url, bearerAuthenticationToken, byggOfficeDto);
                GeneralResponseDto response = doPostRequest(url, bearerAuthenticationToken, byggOfficeDto);
                MessageDto responseMessageDto = (MessageDto) response.getData();
                if (responseMessageDto == null) {
                    responseMessageDto = new MessageDto();
                    responseMessageDto.setMessage(response.getMessage());
                }
                return responseMessageDto;
            } else {
                String s = "Failed to get valid api-token for client type " + client.getClientType();
                logger.error(s);
                throw new RuntimeException(s);
            }
        } else {
            String s = "Unsupported client type " + client.getClientType();
            logger.error(s);
            throw new IllegalArgumentException(s);
        }
    }

}
