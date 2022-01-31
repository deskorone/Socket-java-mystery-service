package mystery;

import fileManager.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Mystery implements MysteryLogic{
    private String text = "";
    private String answer;
    @Override
    public String getMystery() {
        return this.text;
    }

    @Override
    public String getAnswer() {
        return this.answer;
    }

    public Mystery(String text, String answer) {
        this.text = text;
        this.answer = answer;
    }

    public void setText(String text) {
        this.text += text;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Mystery() {

    }

    public String getText() {
        return text;
    }

    @Override
    public boolean checkAmswer() {
        return false;
    }

}
