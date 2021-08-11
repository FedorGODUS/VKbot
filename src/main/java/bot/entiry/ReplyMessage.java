package bot.entiry;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Setter
@Getter
@JsonPropertyOrder(alphabetic = true)
public class ReplyMessage implements Serializable {
    @JsonProperty(value = "user_id")
    Long userId;
    @JsonProperty(value = "random_id")
    Long randomId;
    @JsonProperty(value = "peer_id")
    Long peerId;
    String message;
    String attachment;
    @JsonProperty(value = "group_id")
    Long groupId;


}
