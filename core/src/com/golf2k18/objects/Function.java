package com.golf2k18.objects;

import java.io.Serializable;
import java.util.Stack;

public class Function implements Serializable {

    private Node root;
    private Node xDeriv;
    private Node yDeriv;

    public Function(String[] postFix)
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
                tempRoot.right = tempRight;

                if(postFix[i] != "sin" && postFix[i] != "cos")
                {
                    tempLeft = nodeStack.pop();
                    tempRoot.left = tempLeft;
                }

                nodeStack.push(tempRoot);
            }
        }
        root = nodeStack.peek();

       xDeriv = xDerive(root);
       yDeriv = yDerive(root);
    }

    //For generating nodes in a binary tree
	private class Node implements Serializable
	{
		String value; //Each node has a value (either a number or an operator)
		Node left, right; //Each node has 2 children
		
		Node(String value)
		{
			this.value = value;
			left = right = null;
        }
	}
	
	private boolean isOperator(String s)
	{
		if(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("^") || s.equals("sin") || s.equals("cos"))
		{
			return true;
		}
		return false;
	}

	public double evaluateF(double x,double y){
        return evaluate(root,x,y);
    }
    public double evaluateXDeriv(double x,double y){
        return evaluate(xDeriv,x,y);
    }
    public double evaluateYDeriv(double x,double y){
        return evaluate(yDeriv,x,y);
    }

    private double evaluate(Node root, double xValue, double yValue)
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
		
		Double leftValue = null;
		Double rightValue = null;
		
		if(root.left != null)
			leftValue = evaluate(root.left, xValue, yValue);
		if(root.right != null)
			rightValue = evaluate(root.right, xValue, yValue);
		
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
	
	private Node xDerive(Node root)
	{
		if(!isOperator(root.value))
		{
			if(root.value.equals("x"))
			{
				Node tempNode = new Node("1");
				return tempNode;
			}
			Node tempNode = new Node("0");
			return tempNode;
		}
		
		if(root.value.equals("+"))
		{
			Node tempNode = new Node("+");
			tempNode.left = xDerive(root.left);
			tempNode.right =  xDerive(root.right);
			return tempNode;
		}
		
		if(root.value.equals("-"))
		{
			Node tempNode = new Node("-");
			tempNode.left = xDerive(root.left);
			tempNode.right =  xDerive(root.right);
			return tempNode;
		}
		
		if(root.value.equals("*"))
		{
			Node tempNode = new Node("+");
			tempNode.left = new Node("*");
			tempNode.right = new Node("*");
			
			tempNode.left.left = root.left;
			tempNode.left.right = xDerive(root.right);
			tempNode.right.left = xDerive(root.left);
			tempNode.right.right = root.right;
			
			return tempNode;
		}
		
		if(root.value.equals("/"))
		{
			Node tempNode = new Node("/");
			tempNode.left = new Node("-");
			tempNode.right = new Node("^");
			tempNode.left.left = new Node("*");
			tempNode.left.right = new Node("*");
			tempNode.right.right = new Node("^");
			tempNode.right.right.right = new Node("2");
			
			tempNode.left.left.left = root.right;
			tempNode.left.left.right = xDerive(root.left);
			tempNode.left.right.left = root.left;
			tempNode.left.right.right = xDerive(root.right);
			tempNode.right.right.left = root.right;
			
			return tempNode;
		}
		
		if(root.value.equals("^"))
		{
			Node tempNode = new Node("*");
			tempNode.right = new Node("^");
			tempNode.right.right = new Node("-");
			tempNode.right.right.right = new Node("1");
			 
			tempNode.left = root.right;
			tempNode.right.left = root.left;
			tempNode.right.right.left = root.right;
			
			return tempNode;
		}
		
		if(root.value.equals("sin"))
		{
			Node tempNode = new Node("*");
			tempNode.right = new Node("cos");

			if(root.left == null)
			{
				tempNode.left = xDerive(root.right);
				tempNode.right.right = root.right;
			}
			else
			{
				tempNode.left = xDerive(root.left);	
				tempNode.right.right = root.left;
			}

			return tempNode;
		}

		Node tempNode = new Node("*");
		tempNode.right = new Node("*");		
		tempNode.right.left = new Node("-1");
		tempNode.right.right = new Node("sin");
		
		if(root.left == null)
		{
			tempNode.left = xDerive(root.right);
			tempNode.right.right.right = root.right;
		}
		else
		{
			tempNode.left = xDerive(root.left);	
			tempNode.right.right.right = root.left;
		}
		
		return tempNode;
	}
	
	private Node yDerive(Node root)
	{
		if(!isOperator(root.value))
		{
			if(root.value.equals("y"))
			{
				Node tempNode = new Node("1");
				return tempNode;
			}
			Node tempNode = new Node("0");
			return tempNode;		
		}
		
		if(root.value.equals("+"))
		{
			Node tempNode = new Node("+");
			tempNode.left = yDerive(root.left);
			tempNode.right =  yDerive(root.right);
			return tempNode;
		}
		
		if(root.value.equals("-"))
		{
			Node tempNode = new Node("-");
			tempNode.left = yDerive(root.left);
			tempNode.right =  yDerive(root.right);
			return tempNode;
		}
		
		if(root.value.equals("*"))
		{
			Node tempNode = new Node("+");
			tempNode.left = new Node("*");
			tempNode.right = new Node("*");
			
			tempNode.left.left = root.left;
			tempNode.left.right = yDerive(root.right);
			tempNode.right.left = yDerive(root.left);
			tempNode.right.right = root.right;
			
			return tempNode;
		}
		
		if(root.value.equals("/"))
		{
			Node tempNode = new Node("/");
			tempNode.left = new Node("-");
			tempNode.right = new Node("^");
			tempNode.left.left = new Node("*");
			tempNode.left.right = new Node("*");
			tempNode.right.right = new Node("^");
			tempNode.right.right.right = new Node("2");
			
			tempNode.left.left.left = root.right;
			tempNode.left.left.right = yDerive(root.left);
			tempNode.left.right.left = root.left;
			tempNode.left.right.right = yDerive(root.right);
			tempNode.right.right.left = root.right;
			
			return tempNode;
		}
		
		if(root.value.equals("^"))
		{
			Node tempNode = new Node("*");
			tempNode.right = new Node("^");
			tempNode.right.right = new Node("-");
			tempNode.right.right.right = new Node("1");
			 
			tempNode.left = root.right;
			tempNode.right.left = root.left;
			tempNode.right.right.left = root.right;
			
			return tempNode;
		}
		
		if(root.value.equals("sin"))
		{
			Node tempNode = new Node("*");
			tempNode.right = new Node("cos");

			if(root.left == null)
			{
				tempNode.left = yDerive(root.right);
				tempNode.right.right = root.right;
			}
			else
			{
				tempNode.left = yDerive(root.left);	
				tempNode.right.right = root.left;
			}

			return tempNode;
		}

		Node tempNode = new Node("*");
		tempNode.right = new Node("*");		
		tempNode.right.left = new Node("-1");
		tempNode.right.right = new Node("sin");
		
		if(root.left == null)
		{
			tempNode.left = yDerive(root.right);
			tempNode.right.right.right = root.right;
		}
		else
		{
			tempNode.left = yDerive(root.left);	
			tempNode.right.right.right = root.left;
		}
		
		return tempNode;
	}
	/*
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
	}*/
	
	/*public static void main(String[] args)
	{
		Function f = new Function();
		Node N = f.constructTree(new String[] {"0.2", "y", "*", "sin", "0.1", "x", "*", "+", "0.03", "x", "2", "^", "*", "+"});
		System.out.println(f.yPartial(N, 0, 1, 0.001));
		Node xD = f.xDerive(N);
		Node yD = f.yDerive(N);
		System.out.println(f.evaluate(yD, 0, 1));
	}*/
}
