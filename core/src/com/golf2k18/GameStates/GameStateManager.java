package com.golf2k18.GameStates;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.media.jfxmediaimpl.MediaDisposer;

public class GameStateManager
{
	private Stack<GameState> states;

	public GameStateManager()
	{
		states = new Stack<GameState>();
	}
	public void push(GameState state)
	{
		states.push(state);
	}
	public void pop()
	{
		states.pop();
	}
	public void set(GameState state)
	{
		states.pop();
		states.push(state);
	}
	public void update(float dt)
	{
		states.peek().update(dt);
	}
	public void render(SpriteBatch sb)
	{
		states.peek().render(sb);
	}

}
