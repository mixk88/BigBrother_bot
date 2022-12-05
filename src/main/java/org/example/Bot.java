package org.example;



import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;



public class Bot extends TelegramLongPollingBot {
    String tags = "";
      private final static String CHANELID = "id канала";
    public  final static String SERVICEGROUPID = "I'd сервисной группы";
    private final static String BOTUSERNAME = "IT_bro_bot";
    private final static String TOKEN = " Токен бота";



    public void sendPost(String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(CHANELID);  //id чата куда отвечать
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
    public void sendServMsg(String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(SERVICEGROUPID);
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
        if (message != null && message.hasText() && message.getText().equals("/start")){
            sendAnswer(message, "Привет! Я бот-помошник канала ITeasy!"+
                    "\n введи одну из комманд в зависимости от предмета публикации и я присвою соответствующий тэг - " +
                    "это облегчит сортировку сообщений по темам и контроль контента" +
                    "\n /myProject - если хочешь поделится своим некоммерческим/учебным проектом" +
                    "\n /startUp - проектом на коммерческой основе" +
                    "\n /vacancy - если хочешь разместить вакансию" +
                    "\n /cv - если хочешь поделится своим резюме");

        }
        if (message != null && message.hasText() && message.getText().equals("/myProject")){
           sendAnswer(message, "теперь можешь писать текст своего поста");
            tags = "#учебныйПроект #некоммерческийПроект #мойПроект";
                   }
        if (message != null && message.hasText() && message.getText().equals("/vacancy")){
            sendAnswer(message, "теперь можешь писать текст своего поста");
            tags = "#вакансия";
        }
        if (message != null && message.hasText() && message.getText().equals("/cv")){
            sendAnswer(message, "теперь можешь писать текст своего поста");
            tags = "#резюме";
                   }
        if (message != null && message.hasText() && message.getText().equals("/startUp")){
            sendAnswer(message, "теперь можешь писать текст своего поста");
            tags = "#стартап";
        }

        if (!tags.equals("") && message != null && message.hasText() && rightText == true && !message.getText().equals("/start")
         && !message.getText().equals("/vacancy") && !message.getText().equals("/cv") && !message.getText().equals("/myProject") && !message.getText().equals("/startUp")) {
            sendPost(message.getText() + "\n #userPost "+ tags);
            tags = "";

                   }
        if (message != null && message.hasText() && rightText == false){
            sendAnswer(message, "Похоже ваше сообщение нарушает правила публикации!" +
                    "\n Пожалуйста, проверьте текст и попробуйте еще раз!");
            sendServMsg(message.getText());
        }
      /* if (message != null && message.hasText() && !message.getText().equals("/start")
                && !message.getText().equals("/vacancy") && !message.getText().equals("/cv") &&
                !message.getText().equals("/myProject") && textCheck == false){
            sendAnswer(message, "Твой текст вызывает у меня сомнения, я отправлю его на проверку. Если все ОК, он будет опубликован");
            sendServMsg(message.getText());
             } */ // Это условие для проверки на текст без тегов - неявного содержания

         }

    @Override
    public String getBotUsername() {
        return BOTUSERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }
}
