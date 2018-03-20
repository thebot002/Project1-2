package com.golf2k18.GameStates;

import java.util.Stack;

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
}
