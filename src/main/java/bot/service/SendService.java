package bot.service;

import bot.entiry.CallbackMessage;
import bot.entiry.Message;

import java.net.URI;

public interface SendService {

    void send(CallbackMessage message);

}
