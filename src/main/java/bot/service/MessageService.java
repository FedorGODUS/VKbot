package bot.service;

import bot.entiry.Callback;
import bot.entiry.CallbackMessage;
import bot.entiry.Message;

public interface MessageService {

    String receiveCallback(Callback callback);

    CallbackMessage getMessageFromCallback(Callback callback);
}
