package bot.service;

import bot.config.VkConfig;
import bot.entiry.Callback;
import bot.entiry.CallbackMessage;
import bot.entiry.ReplyMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final VkConfig config;
    private final SendService sendService;

    @Override
    public String receiveCallback(Callback callback) {
        check(callback);
        switch (Objects.requireNonNull(callback.getType())) {
            case message_new:
                CallbackMessage callbackMessage = getMessageFromCallback(callback);
                createReplyText(callbackMessage);
                return "ok";
            case confirmation:
                return config.getConfirmation();
            default:
                return ("reply_message");
        }
    }

    public CallbackMessage getMessageFromCallback(Callback callback) {
        Map<String, Object> body = callback.getObject();
        System.out.println(body.values());
        System.out.println(body.keySet());
        return CallbackMessage.builder()
                .id(Long.parseLong(String.valueOf(body.get("id"))))
                .fromId(Long.parseLong(String.valueOf(body.get("from_id"))))
                .peerId(Long.parseLong(String.valueOf(body.get("peer_id"))))
                .text(String.valueOf(body.get("text")))
                .date(Long.parseLong(String.valueOf(body.get("date"))))
                .attachment(String.valueOf(body.get("attachments")))
                .groupId(callback.getGroupId())
                .build();
    }

    public void createReplyText(CallbackMessage callbackMessage) {
        String answer = "Вы напечатали: ".concat(callbackMessage.getText());
        if (!callbackMessage.getAttachment().equals("[]")) {
            answer = answer.concat("\nВы также добавили атачмент, но мы его не обрабатываем");
        }
        ReplyMessage replyMessage = ReplyMessage.builder()
                .groupId(callbackMessage.getGroupId())
                .peerId(callbackMessage.getPeerId())
                .message(answer)
                .randomId((long) callbackMessage.hashCode())
                .build();
        sendService.send(replyMessage);
    }

    private void check(Callback callback) {
        if (!config.getSecret().equals(callback.getSecret())) {
            throw new InvalidParameterException("secret");
        }
    }
}
