package bot.service;

import bot.config.VkConfig;
import bot.entiry.Callback;
import bot.entiry.CallbackMessage;
import bot.entiry.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final VkConfig config;
    private final SendService sendService;

    @Override
    public String receiveCallback(Callback callback) {
        switch (Objects.requireNonNull(callback.getType())){
            case message_new:
                sendService.send(getMessageFromCallback(callback));
                return "ok";
            case confirmation:
                return config.getConfirmation();
            default:
                return "not ok";
        }
    }

    @Override
    public CallbackMessage getMessageFromCallback(Callback callback) {
        Map<String, Object> body = callback.getObject();
        return CallbackMessage.builder()
                .id(Long.parseLong(String.valueOf(body.get("id"))))
                .fromId(Long.parseLong(String.valueOf(body.get("from_id"))))
                .peerId(Long.parseLong(String.valueOf(body.get("peer_id"))))
                .text(String.valueOf(body.get("text")))
                .date(Long.parseLong(String.valueOf(body.get("date"))))
                .groupId(callback.getGroupId())
                .build();
    }
}
