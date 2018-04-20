package com.golf2k18.states;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StateManager
{
	private Stack<State> states;

	public StateManager()
	{
		states = new Stack<State>();
	}
	public void push(State state)
	{
	    state.create();
		states.push(state);
	}
	public void pop()
	{
	    //states.peek().dispose();
		states.pop();
	}
	public void set(State state)
	{
	    //states.peek().dispose();
		states.pop();
		state.create();
		states.push(state);
	}

	public void render(SpriteBatch sb)
	{
        //states.peek().update(1);
        states.peek().render();
	}

}
