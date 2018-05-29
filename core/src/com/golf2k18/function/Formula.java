package com.golf2k18.function;

import java.io.Serializable;
import java.util.Stack;

/**
 * This class handles the storing, evaluating an deriving of functions.
 */
public class Formula implements Function, Serializable {

    private Node root;
    private Node xDeriv;
    private Node yDeriv;

	/**
	 * Constructor for the formula class, it stores the formula, and it's x and y derivatives in binary expression trees.
	 * @param postFix array that holds a formula in a post fix notation.
	 */
    public Formula(String[] postFix)
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

	/**
	 * Inner Class that is the nodes of the binary expression tree.
	 */
	private class Node implements Serializable
	{
		String value; //Each node has a value (either a number or an operator)
		Node left, right; //Each node has 2 children

		/**
		 * Constructor for the node class.
		 * @param value the operator or value that is stored in the node.
		 */
		Node(String value)
		{
			this.value = value;
			left = right = null;
        }
	}

	/**
	 * Method that checks if the value of a node is an operator or not.
	 * @param s the value of the checked node.
	 */
	private boolean isOperator(String s)
	{
		if(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("^") || s.equals("sin") || s.equals("cos"))
		{
			return true;
		}
		return false;
	}

	/**
	 * Calls the evaluation method for the main tree.
	 * @param x the x position on the function.
	 * @param y the y position on the function.
	 * @return the value of the evaluated tree.
	 */
	public float evaluateF(float x,float y){
        return evaluate(root,x,y);
    }

	/**
	 * Calls the evaluation method for the x derivation tree.
	 * @param x the x position on the function.
	 * @param y the y position on the function.
	 * @return the evaluated value of the derived tree.
	 */
    public float evaluateXDeriv(float x,float y){
        return evaluate(xDeriv,x,y);
    }

	/**
	 * Calls the evaluation method for the y derivation tree.
	 * @param x the x position on the function.
	 * @param y the y position on the function.
	 * @return the evaluated value of the derived tree.
	 */
    public float evaluateYDeriv(float x,float y){
        return evaluate(yDeriv,x,y);
    }

	/**
	 * Evaluates the value of a given tree.
	 * @param xValue the x position on the function.
	 * @param yValue the y position on the function.
	 * @return the value of the evaluated tree.
	 */
    private float evaluate(Node root, float xValue, float yValue)
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
			return Float.parseFloat(root.value);
		}
		
		Float leftValue = null;
		Float rightValue = null;
		
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
			return (float)Math.pow(leftValue, rightValue);
		
		if(root.value.equals("sin"))
			if(leftValue == null)
			{
				return (float)Math.sin(rightValue);
			}
			else
			{
				return (float)Math.sin(leftValue);
			}

		if(leftValue == null)
		{
			return (float)Math.cos(rightValue);
		}
		else
		{
			return (float)Math.cos(leftValue);
		}
	}

	/**
	 * Calculates the partially derivation of a tree in the x direction.
	 * @param root the root node of the tree from which to start deriving.
	 * @return the tree of the partially derived function.
	 */
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

	/**
	 * Calculates the partially derivation of a tree in the y direction.
	 * @param root the root node of the tree from which to start deriving.
	 * @return the tree of the partially derived function.
	 */
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
}
