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
		
		if(root.value.equals("+"))
		{
			root.left = xDerive(root.left);
			root.right = xDerive(root.right);
			return root;
		}
		
		if(root.value.equals("-"))
		{
			root.left = xDerive(root.left);
			root.right = xDerive(root.right);
			return root;
		}
		
		if(root.value.equals("*"))
		{
			Node tempLeft = new Node("*");
			Node tempRight = new Node("*");
			
			tempLeft.left = root.left;
			tempLeft.right = xDerive(root.right);
			tempRight.left = xDerive(root.left);
			tempRight.right = root.right;
			
			root.value = "+";
			root.left = tempLeft;
			root.right = tempRight;
			return root;
		}
		
		if(root.value.equals("/"))
		{
			Node tempLeft = new Node("-");
			Node tempRight = new Node("^");
			tempLeft.left = new Node("*");
			tempLeft.right = new Node("*");
			tempRight.right = new Node("^");
			tempRight.right.right = new Node("2");
			
			tempLeft.left.left = root.right;
			tempLeft.left.right = xDerive(root.left);
			tempLeft.right.left = root.left;
			tempLeft.right.right = xDerive(root.right);
			tempRight.right.left = root.right;
			
			root.left = tempLeft;
			root.right = tempRight;
			return root;
		}
		
		if(root.value.equals("^"))
		{
			Node tempRight = new Node("^");
			tempRight.right = new Node("-");
			tempRight.right.right = new Node("1");
			 
			Node tempLeft = root.right;
			tempRight.left = root.left;
			tempRight.right.left = root.right;
			
			root.left = tempLeft;
			root.right = tempRight;
			root.value = "*";
			return root;
		}
		
		if(root.value.equals("sin"))
		{
			Node tempRight = new Node("cos");
			
			Node tempLeft;
			
			if(root.left.value == null)
			{
				tempLeft = xDerive(root.right);
				tempRight.right = root.right;
			}
			else
			{
				tempLeft = xDerive(root.left);	
				tempRight.right = root.left;
			}
			
			root.left = tempLeft;
			root.right = tempRight.right;
			root.value = "*";
			return root;
		}

		Node tempLeft;
		Node tempRight = new Node("*");		
		tempRight.left = new Node("-1");
		tempRight.right = new Node("sin");
		
		if(root.left.value == null)
		{
			tempLeft = xDerive(root.right);
			tempRight.right.right = root.right;
		}
		else
		{
			tempLeft = xDerive(root.left);	
			tempRight.right.right = root.left;
		}
		
		root.left = tempLeft;
		root.right = tempRight;
		root.value = "*";
		return root;
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
