package com.golf2k18.objects;

import java.util.Stack;

public class Function 
	{
	
	//For generating nodes in a binary tree
	class Node
	{
		String value; //Each node has a value (either a number or an operator)
		Node left, right; //Each node has 2 children
		
		Node(String value)
		{
			this.value = value;
			left = right = null;
		}		
	}
	
	boolean isOperator(String s)
	{
		if(s == "+" || s == "-" || s == "*" || s == "/" || s == "^" || s == "sin" || s == "cos")
		{
			return true;
		}
		return false;
	}
	
	Node constructTree(String[] postFix)
	{
		Stack<Node> nodeStack = new Stack();
		Node tempRoot, tempLeft, tempRight;
		
		for(int i = 0; i < postFix.length; i++)
		{
			tempRoot = new Node(postFix[i]);
			
			if(!isOperator(postFix[i]))
			{
				nodeStack.push(tempRoot);
			}
			else
			{
				tempRight = nodeStack.pop();
				tempLeft = nodeStack.pop();
				
				tempRoot.left = tempLeft;
				tempRoot.right = tempRight;
				
				nodeStack.push(tempRoot);
			}
		}
		
		tempRoot = nodeStack.peek();
		return tempRoot;
	}
	
	
	double evaluate(Node node, double xValue, double yValue)
	{
		if(!isOperator(node.value))
		{
			if(node.value.equals("x"))
			{
				return xValue;
			}
			else if(node.value.equals("y"))
			{
				return yValue;
			}
			return Double.parseDouble(node.value);			
		}
		
		Double leftValue = evaluate(node.left, xValue, yValue);
		Double rightValue = evaluate(node.right, xValue, yValue);
		
		if(node.value.equals("+"))
			return leftValue + rightValue;
		
		if(node.value.equals("-"))
			return leftValue - rightValue;
		
		if(node.value.equals("*"))
			return leftValue * rightValue;
		
		if(node.value.equals("/"))
			return leftValue / rightValue;
		
		if(node.value.equals("^"))
			return Math.pow(leftValue, rightValue);
		
		if(node.value.equals("sin"))
			if(leftValue == null)
			{
				return Math.sin(rightValue);
			}
			else
			{
				return Math.sin(leftValue);					
			}

		if(leftValue == null)
		{
			return Math.cos(rightValue);
		}
		else
		{
			return Math.cos(leftValue);					
		}
	}
	
	public static void main(String[] args)
	{
		Function test = new Function();
		String[] string = {"5","x","/"};
		Node root = test.constructTree(string);
		System.out.println(test.evaluate(root,0.1,0));
	}
}
