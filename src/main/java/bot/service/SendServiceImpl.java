package bot.service;

import bot.config.VkConfig;
import bot.entiry.MessageResponse;
import bot.entiry.ReplyMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SendServiceImpl implements SendService {
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final VkConfig vkConfig;

    @Override
    public void send(ReplyMessage replyMessage) {
        ResponseEntity<MessageResponse> responseEntity = restTemplate.postForEntity(createUri(replyMessage), null, MessageResponse.class);
        validate(responseEntity);
    }


    public URI createUri(ReplyMessage replyMessage) {
        try {
            MultiValueMap<String, String> map = objectMapper.convertValue(replyMessage, LinkedMultiValueMap.class);
            return UriComponentsBuilder.fromHttpUrl("https://api.vk.com/method/messages.send")
                    .queryParam("access_token", vkConfig.getAccess_token())
                    .queryParam("v", vkConfig.getV())
                    .queryParams(map)
                    .build()
                    .toUri();
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }
    }

    public void validate(ResponseEntity<MessageResponse> responseEntity) {
        Optional<MessageResponse> body = Optional.ofNullable(responseEntity.getBody());
        if (body.isPresent() && body.get().getError() != null && body.get().getError().getErrorCode() != null) {
            System.out.println(body.get().getError().getErrorMsg());
        }
    }


}
