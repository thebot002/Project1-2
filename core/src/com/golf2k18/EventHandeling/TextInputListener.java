package com.golf2k18.EventHandeling;

import com.badlogic.gdx.Input;

public class TextInputListener implements Input.TextInputListener
{
    private String input;

    @Override
    public void input(String text)
    {
        this.input = text;
    }

    @Override
    public void canceled()
    {
        this.input = "";
    }

    public String getText()
    {
        return this.input;
    }

}
