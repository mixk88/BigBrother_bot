package org.example;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) throws TelegramApiException {


        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {

            telegramBotsApi.registerBot(new Bot());

        } catch (TelegramApiException e) {

            e.printStackTrace();

        }
    }
    public void sendMsg(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());  //id чата куда отвечать
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
            if (message !=null && message.hasText()){
                switch (message.getText()){
                    case "/help":
                        sendMsg(message, "can i  help yoy?");
                        break;
                    case "/newpost":
                        sendMsg(message, "new post is");
                    default:
                }
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
