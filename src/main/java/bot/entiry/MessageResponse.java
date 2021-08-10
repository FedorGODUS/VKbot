package bot.entiry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    @JsonProperty(value = "peer_id")
    Long peerId;
    @JsonProperty(value = "message_id")
    Long messageId;
    MessageError error;

    @Getter
    public static class MessageError {
        @JsonProperty(value = "error_code")
        Long errorCode;
        @JsonProperty(value = "error_msg")
        String errorMsg;
    }
}
