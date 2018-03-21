
public class function
{
	
	//For generating nodes in a binary tree
	class Node
	{
		String value;
		Node left, right;
		
		Node(String value)
		{
			this.value = value;
			left = right = null;
		}		
	
	
	public class functionTree
	{
		boolean isOperator(String s)
		{
			if(s == "+" || s == "-" || s == "*" || s == "/" || s == "^" || s == "sin" || s == "cos" || s == "sqrt")
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
				return Math.sqrt(rightValue);
			
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
	
	public void main(String[] args)
	{
		function test = new function();
		Node root = new Node("+");
		root.left = new Node("5");
		root.right = new Node("4");
		functionTree ft = new functionTree();
		System.out.println(ft.evaluate(root));
	}
	}
}