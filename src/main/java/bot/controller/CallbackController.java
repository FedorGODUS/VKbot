package bot.controller;
import bot.entiry.Callback;
import bot.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/callback")
@RequiredArgsConstructor
public class CallbackController {

    private final MessageService messageService;


    @PostMapping
    @ResponseBody
    public ResponseEntity<String> getCallback(@RequestBody Callback callback){
        System.out.println(messageService.receiveCallback(callback));
        return new ResponseEntity<>(messageService.receiveCallback(callback), HttpStatus.OK);
    }
}
