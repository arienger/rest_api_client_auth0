package no.eg.nexstephub.client.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import no.eg.nexstephub.client.domain.BearerAuthenticationToken;
import no.eg.nexstephub.client.domain.Client;
import no.eg.nexstephub.client.model.GeneralResponseDto;
import no.eg.nexstephub.client.model.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private Client client;

    @Autowired
    private RestTemplate restTemplate;

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

    private ResponseEntity<GeneralResponseDto> doGetRequest(String url, BearerAuthenticationToken bearerAuthenticationToken) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        String authToken = bearerAuthenticationToken.getAuthorizationToken();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, authToken);

        HttpEntity request = new HttpEntity<>("", httpHeaders);
        ResponseEntity response = null;
        response = restTemplate.exchange(url, HttpMethod.GET, request, com.fasterxml.jackson.databind.JsonNode.class);

        try {
            if (!response.getStatusCode().is2xxSuccessful()) {
                String s = "Failed to call url " + url + ", status " + response.getStatusCode();
                logger.error(s);
                throw new RuntimeException(s);
            } else {
                Object object = response.getBody();
                logger.info("object " + object);
                if (object instanceof com.fasterxml.jackson.databind.node.ObjectNode) {
                    com.fasterxml.jackson.databind.node.ObjectNode objectNode = (com.fasterxml.jackson.databind.node.ObjectNode) object;
                    GeneralResponseDto<MessageDto> messageDtoGeneralResponseDto = null;
                    if (objectNode != null && objectNode.isObject()) {
                        messageDtoGeneralResponseDto = parseResult((JsonNode)objectNode);
                    }
                    if (messageDtoGeneralResponseDto == null) {
                        throw new IllegalArgumentException("Failed to parse result");
                    }

                    return new ResponseEntity<>(messageDtoGeneralResponseDto, response.getStatusCode());
                } else {
                    String s = "Invalid return type: " + (object == null ? null : object.getClass().getName()) + ". Expected: com.fasterxml.jackson.databind.node.ObjectNode";
                    logger.error(s);
                    throw new IllegalArgumentException(s);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to map result to object",e);
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    private ResponseEntity<GeneralResponseDto> doPostRequest(String url, BearerAuthenticationToken bearerAuthenticationToken, MessageDto messageDto) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        String authToken = bearerAuthenticationToken.getAuthorizationToken();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, authToken);

        HttpEntity request = new HttpEntity<>(messageDto, httpHeaders);
        ResponseEntity response = null;
        response = restTemplate.exchange(url, HttpMethod.POST, request, JsonNode.class);

        try {
            if (!response.getStatusCode().is2xxSuccessful()) {
                String s = "Failed to call url " + url + ", status " + response.getStatusCode();
                logger.error(s);
                throw new RuntimeException(s);
            } else {
                Object object = response.getBody();
                logger.info("object " + object);
                if (object instanceof com.fasterxml.jackson.databind.node.ObjectNode) {
                    com.fasterxml.jackson.databind.node.ObjectNode objectNode = (com.fasterxml.jackson.databind.node.ObjectNode) object;
                    GeneralResponseDto<MessageDto> messageDtoGeneralResponseDto = null;
                    if (objectNode != null && objectNode.isObject()) {
                        messageDtoGeneralResponseDto = parseResult((JsonNode)objectNode);
                    }
                    if (messageDtoGeneralResponseDto == null) {
                        throw new IllegalArgumentException("Failed to parse result");
                    }

                    return new ResponseEntity<>(messageDtoGeneralResponseDto, response.getStatusCode());
                } else {
                    String s = "Invalid return type: " + (object == null ? null : object.getClass().getName()) + ". Expected: com.fasterxml.jackson.databind.node.ObjectNode";
                    logger.error(s);
                    throw new IllegalArgumentException(s);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to map result to object",e);
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    private GeneralResponseDto<MessageDto> parseResult(JsonNode jsonNode) {
        if (jsonNode == null || !jsonNode.isObject()) {
            return null;
        }
        GeneralResponseDto<MessageDto> messageDtoGeneralResponseDto = new GeneralResponseDto<>();
        JsonNode httpStatusCodeNode = jsonNode.get("httpStatusCode");
        if (httpStatusCodeNode != null && httpStatusCodeNode.isInt()) {
            messageDtoGeneralResponseDto.setHttpStatusCode(httpStatusCodeNode.intValue());
        }
        MessageDto messageDto = null;
        JsonNode dataNode = jsonNode.get("data");
        if (dataNode != null && dataNode.isObject()) {
            JsonNode messageNode = dataNode.get("message");
            if (messageNode != null && messageNode.isTextual()) {
                String message = messageNode.asText();
                messageDto = new MessageDto();
                messageDto.setMessage(message);
            }
        }
        messageDtoGeneralResponseDto.setData(messageDto);

        return messageDtoGeneralResponseDto;
    }
}
