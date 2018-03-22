
public class function
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
	
	public class functionTree
	{
		boolean isOperator(String s)
		{
			if(s == "+" || s == "-" || s == "*" || s == "/" || s == "^" || s == "sqrt" || s == "sin" || s == "cos") //sqrt = ^0.5, is it needed?
			{
				return true;
			}
			return false;
		}
		
		double evaluate(Node node)
		{
			if(!isOperator(node.value))
				return Double.parseDouble(node.value);
			
			Double leftValue = evaluate(node.left);
			Double rightValue = evaluate(node.right);
			
			if(node.value == "+")
				return leftValue + rightValue;
			
			if(node.value == "-")
				return leftValue - rightValue;
			
			if(node.value == "*")
				return leftValue * rightValue;
			
			if(node.value == "/")
				return leftValue / rightValue;
			
			if(node.value == "^")
				return Math.pow(leftValue, rightValue);
			
			if(node.value == "sqrt")
				if(leftValue == null)
				{
					return Math.sqrt(rightValue);
				}
				else
				{
					return Math.sqrt(leftValue);					
				}
			
			if(node.value == "sin")
				if(leftValue == null)
				{
					return Math.sin(rightValue);
				}
				else
				{
					return Math.sin(leftValue);					
				}
			//sure?
			if(leftValue == null)
			{
				return Math.cos(rightValue);
			}
			else
			{
				return Math.cos(leftValue);					
			}
		}
	}
	
	public static void main(String[] args)
	{
		function test = new function();
		Node root = test.new Node("*");
		root.left = test.new Node("^");
		root.left.left = test.new Node("2");
		root.left.right = test.new Node("3");
		root.right = test.new Node("4");
		functionTree ft = test.new functionTree();
		System.out.println(ft.evaluate(root));
	}
}