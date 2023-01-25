package no.eg.nexstephub.client.service;

import com.fasterxml.jackson.databind.JsonNode;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static no.eg.nexstephub.client.util.DateTimeUtil.dateTimeFormatter;

@Service
public abstract class AbstractService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractService.class);

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    protected AuthorizationService authorizationService;

    @Autowired
    protected Client client;

    public AbstractService() {
    }

    protected GeneralResponseDto doPostRequest(String url, BearerAuthenticationToken bearerAuthenticationToken, Object messageDto) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        String authToken = bearerAuthenticationToken.getAuthorizationToken();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, authToken);

        HttpEntity request = new HttpEntity<>(messageDto, httpHeaders);
        ResponseEntity response = null;

        try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, JsonNode.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                logger.error("Failed to call '" + url + "'. Status " + response.getStatusCode());
                return (GeneralResponseDto) response.getBody();
            } else {
                Object object = response.getBody();
                logger.info("object " + object);
                if (object instanceof com.fasterxml.jackson.databind.node.ObjectNode) {
                    com.fasterxml.jackson.databind.node.ObjectNode objectNode = (com.fasterxml.jackson.databind.node.ObjectNode) object;
                    GeneralResponseDto<MessageDto> messageDtoGeneralResponseDto = null;
                    if (objectNode != null && objectNode.isObject()) {
                        messageDtoGeneralResponseDto = parseResult((JsonNode) objectNode);
                    }
                    if (messageDtoGeneralResponseDto == null) {
                        throw new IllegalArgumentException("Failed to parse result");
                    }

                    return messageDtoGeneralResponseDto;
                } else {
                    String s = "Invalid return type: " + (object == null ? null : object.getClass().getName()) + ". Expected: com.fasterxml.jackson.databind.node.ObjectNode";
                    logger.error(s);
                    throw new IllegalArgumentException(s);
                }
            }
        } catch (HttpClientErrorException e) {
            logger.error("Failure in request to URL '" +url+"'", e);
            GeneralResponseDto<MessageDto> result = new GeneralResponseDto<>();
            result.setHttpStatusCode(e.getStatusCode().value());
            result.setOccurred_at(LocalDateTime.now().format(dateTimeFormatter));
            result.setMessage(e.getMessage());
            return result;
        }
        catch (Exception e) {
            logger.error("Failed to map result to object", e);
            throw e;
        }
    }

    protected GeneralResponseDto<MessageDto> parseResult(JsonNode jsonNode) {
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

    protected ResponseEntity<GeneralResponseDto> doGetRequest(String url, BearerAuthenticationToken bearerAuthenticationToken) {
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
                        messageDtoGeneralResponseDto = parseResult((JsonNode) objectNode);
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
            logger.error("Failed to map result to object", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
