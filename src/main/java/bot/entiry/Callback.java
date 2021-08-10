package bot.entiry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class Callback {
    @JsonProperty(value = "type")
    private CallbackType type;
    private Map<String, Object> object;
    @JsonProperty(value = "group_id")
    private Long groupId;
    private String secret;
    @JsonProperty(value = "event_id")
    private String eventId;
}
