package org.example;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) throws TelegramApiException {


        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {

            telegramBotsApi.registerBot(new Bot());

        } catch (TelegramApiException e) {

            e.printStackTrace();

        }
    }
    public void sendPost(String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId("-1001742221166");  //id чата куда отвечать
        //sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);

            try{
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
    }
    public void sendAnswer(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);

        try{
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();
        boolean rightText;
        try {
            rightText = Censor.check(message.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (message != null && message.hasText() && rightText == true) {
            sendPost(message.getText()+ "\n #usersPost");
            }
        if (message != null && message.hasText() && rightText == false){
            sendAnswer(message, "Похоже ваше сообщение нарушает правила публикации!" +
                    "\n Пожалуйста, проверьте текст и попробуйте еще раз!");
        }
        }


    @Override
    public String getBotUsername() {
        return "IT_bro_bot";
    }

    @Override
    public String getBotToken() {
        return "5864814346:AAEC9rLKdp7HI4lykEJ_hppZeW3e6ViQzlQ";
    }

}
