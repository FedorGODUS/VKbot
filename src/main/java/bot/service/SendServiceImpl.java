package bot.service;

import bot.config.VkConfig;
import bot.entiry.CallbackMessage;
import bot.entiry.Message;
import bot.entiry.MessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SendServiceImpl implements SendService {
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final VkConfig vkConfig;

    @Override
    public void send(CallbackMessage callbackMessage) {
        Message message = createMessage(callbackMessage);
        message.setRandomId((long) callbackMessage.hashCode());
        ResponseEntity<MessageResponse> responseEntity = restTemplate.postForEntity(createUri(message), null, MessageResponse.class);
        validate(responseEntity);
    }


    public Message createMessage(CallbackMessage callbackMessage) {
        return Message.builder()
                .groupId(callbackMessage.getGroupId())
                .peerId(callbackMessage.getPeerId())
                .message("Вы напечатали: ".concat(callbackMessage.getText()))
                .build();
    }


    public URI createUri(Message message) {
        try {
            MultiValueMap<String, String> map = objectMapper.convertValue(message, LinkedMultiValueMap.class);
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
