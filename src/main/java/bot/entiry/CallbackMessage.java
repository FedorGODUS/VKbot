package bot.entiry;


import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CallbackMessage {
    private Long id;
    private Long date;
    private Long peerId;
    private Long fromId;
    private String text;
    private Long groupId;
}
