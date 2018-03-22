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
		if(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("^") || s.equals("sin") || s.equals("cos"))
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
	
	
	double evaluate(Node root, double xValue, double yValue)
	{
		if(!isOperator(root.value))
		{
			if(root.value.equals("x"))
			{
				return xValue;
			}
			else if(root.value.equals("y"))
			{
				return yValue;
			}
			return Double.parseDouble(root.value);			
		}
		
		Double leftValue = evaluate(root.left, xValue, yValue);
		Double rightValue = evaluate(root.right, xValue, yValue);
		
		if(root.value.equals("+"))
			return leftValue + rightValue;
		
		if(root.value.equals("-"))
			return leftValue - rightValue;
		
		if(root.value.equals("*"))
			return leftValue * rightValue;
		
		if(root.value.equals("/"))
			return leftValue / rightValue;
		
		if(root.value.equals("^"))
			return Math.pow(leftValue, rightValue);
		
		if(root.value.equals("sin"))
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
	
	Node xDerive(Node root)
	{
		if(!isOperator(root.value))
		{
			if(root.value.equals("x"))
			{
				root.value = "1";
				return root;
			}
			root.value = "0";
			return root;		
		}
		
		Node tempLeft = xDerive(root.left);
		Node tempRight = xDerive(root.right);
		
		if(root.value.equals("+"))
			return leftValue + rightValue;
		
		if(root.value.equals("-"))
			return leftValue - rightValue;
		
		if(root.value.equals("*"))
			return leftValue * rightValue;
		
		if(root.value.equals("/"))
			return leftValue / rightValue;
		
		if(root.value.equals("^"))
			return Math.pow(leftValue, rightValue);
		
		if(root.value.equals("sin"))
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
	
	double xPartial(Node root, double xValue, double yValue, double delta)
	{
		double startPoint = evaluate(root, xValue - delta, yValue);
		double endPoint = evaluate(root, xValue + delta, yValue);
		
		double xSlope = (endPoint - startPoint) / (2 * delta);
		
		return xSlope;
	}
	
	double yPartial(Node root, double xValue, double yValue, double delta)
	{
		double startPoint = evaluate(root, xValue, yValue - delta);
		double endPoint = evaluate(root, xValue, yValue + delta);
		
		double ySlope = (endPoint - startPoint) / (2 * delta);
		
		return ySlope;
	}
}
