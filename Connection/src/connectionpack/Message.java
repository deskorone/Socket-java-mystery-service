package connectionpack;

import java.io.Serializable;

public class Message implements Serializable {

    private String textMessage;
    private TypeMessage typeMessage;

    public void setTypeMessage(TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public Message(TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
    }

    public Message(String textMessage, TypeMessage typeMessage) {
        this.textMessage = textMessage;
        this.typeMessage = typeMessage;
    }

    public Message(String textMessage) {
        this.textMessage = textMessage;
    }





}
