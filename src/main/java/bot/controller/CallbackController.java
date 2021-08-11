package bot.controller;

import bot.entiry.Callback;
import bot.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/callback")
@RequiredArgsConstructor
public class CallbackController {

    private final MessageService messageService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> getCallback(@RequestBody Callback callback) {
        return new ResponseEntity<>(messageService.receiveCallback(callback), HttpStatus.OK);
    }
}
