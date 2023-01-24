package no.eg.nexstephub.client.service;

import no.eg.nexstephub.client.domain.BearerAuthenticationToken;
import no.eg.nexstephub.client.domain.Client;
import no.eg.nexstephub.client.model.GeneralResponseDto;
import no.eg.nexstephub.client.model.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MessageService extends AbstractService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private Client client;

    public MessageService() {
    }

    public MessageDto postMessage(MessageDto messageDto) {
        if (client.getClientType() == Client.ClientTypeEnum.AUTH0) {
            BearerAuthenticationToken bearerAuthenticationToken = authorizationService.getBearerAuthenticationToken();
            if (bearerAuthenticationToken != null && !bearerAuthenticationToken.isExpired()) {
                String url = "http://localhost:8080/api/private/message";
                ResponseEntity<GeneralResponseDto> response = doPostRequest(url, bearerAuthenticationToken, messageDto);
                MessageDto responseMessageDto = (MessageDto) response.getBody().getData();
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

    public MessageDto getMessage() {
        if (client.getClientType() == Client.ClientTypeEnum.AUTH0) {
            BearerAuthenticationToken bearerAuthenticationToken = authorizationService.getBearerAuthenticationToken();
            if (bearerAuthenticationToken != null && !bearerAuthenticationToken.isExpired()) {
                String url = "http://localhost:8080/api/private/ping";
                ResponseEntity<GeneralResponseDto> response = doGetRequest(url, bearerAuthenticationToken);
                MessageDto messageDto = (MessageDto) response.getBody().getData();
                return messageDto;
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
