package com.golf2k18.GameStates;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.media.jfxmediaimpl.MediaDisposer;

public class GameStateManager
{
	private Stack<GameState> states;

	public GameStateManager()
	{
		states = new Stack<>();
	}
	public void push(GameState state)
	{
	    state.create();
		states.push(state);
	}
	public void pop()
	{
	    states.peek().dispose();
		states.pop();
	}
	public void set(GameState state)
	{
	    states.peek().dispose();
		states.pop();
		state.create();
		states.push(state);
	}

	public void render(SpriteBatch sb)
	{
		states.peek().render(sb);
	}

}
