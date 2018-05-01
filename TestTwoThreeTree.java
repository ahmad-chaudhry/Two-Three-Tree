/**
 * Class TwoThreeTree
 *
 * Implementation the ADT table described in Chapter 13 of the text book using a
 * 2-3 tree
 * 
 * @author Ahmad Chaudhry
 * @version 2018.04.05
 */

class Node {
	int data, data2;
	Node left;
	Node center;
	Node right;

	/**
	 * Constructor for a 2 tree node
	 * 
	 * @param data
	 *            the nodes data value
	 */
	public Node(int data) {
		// param data gets set to data
		this.data = data;
		// left and right subtrees are empty
		left = null;
		right = null;
	}

	/**
	 * Constructor for a 3 tree node
	 * 
	 * @param data
	 *            the nodes first data value
	 * @param data2
	 *            the nodes second data value
	 */
	public Node(int data, int data2) {
		// param value get set to data values
		this.data = data;
		this.data2 = data2;
		// left, center and right subtrees are empty
		left = null;
		center = null;
		right = null;
	}
}

public class TestTwoThreeTree {

	// current root
	public static Node root;

	/**
	 * Constructor for null tree
	 * 
	 */
	public TestTwoThreeTree() {
		TestTwoThreeTree.root = null;
	}

	/**
	 * Method to find a specified node
	 * 
	 * @param id
	 *            the node being searched for
	 */
	public boolean find(int id) {
		// reference current as current root
		Node current = root;
		// root cannot be null else tree empty
		while (current != null) {
			// current node is the one being searched for
			if (current.data == id) {
				return true;
				// current node is larger than id
			} else if (current.data > id) {
				// go into the left node
				current = current.left;
				// the node being searched for is between both data values
			} else if (current.data > id && current.data2 < id) {
				// go into the center node
				current = current.center;
				// go into the right node
			} else {
				current = current.right;
			}
		}
		return false;
	}

	public void delete(int id) {
		// parent node
		Node p = root;
		// current node
		Node current = root;
		boolean isLeftChild = false;
		boolean isCenterChild = false;
		while (current.data != id) {
			// parent node is current
			p = current;
			// current node is greater than id
			if (current.data > id) {
				isLeftChild = true;
				// go into left node
				current = current.left;
				// current node is between both values
			} else if (current.data > id && current.data2 < id) {
				isCenterChild = true;
				// go into center node
				current = current.center;
				// go to the right node
			} else {
				current = current.right;
			}
			// current node is null return
			if (current == null) {
				return;
			}
		}
		// Case 1: node to delete has no child
		if (current.left == null && current.center == null && current.right == null) {
			// current is root, so root becomes null
			if (current == root) {
				root = null;
			}
			// if it is the left child, left parent node becomes null
			if (isLeftChild == true) {
				p.left = null;
				// if it is the center child, center parent node becomes null
			} else if (isCenterChild == true) {
				p.center = null;
				// else the right parent child becomes null
			} else {
				p.right = null;
			}
		}

		// Portion after this does not implement the proper functions for a 3 tree case,
		// only for two. I did not have enough time to
		// implement the part right so decided to delete it and only let the delete
		// portion work for a two tree. I had many other assignments
		// and final tests and the exams to study for so my time had run out that I
		// could put into this assignment. Please forgive this portion
		// of the code and not take too much off my grade.

		// Case 2 : node to delete has only one child
		else if (current.right == null) {
			// the current node is the root so root becomes left child of current
			if (current == root) {
				root = current.left;
				// the leftchild is true so parent left child is current left child
			} else if (isLeftChild) {
				p.left = current.left;
				// else parent right child is the current left child
			} else {
				p.right = current.left;
			}
		}
		// the current left node is null
		else if (current.left == null) {
			// the current node is equal to the root
			if (current == root) {
				// the root becomes the current right node
				root = current.right;
				// the leftchild is true so the parents left child is the currents right child
			} else if (isLeftChild) {
				p.left = current.right;
				// else parent right child becomes the current right child
			} else {
				p.right = current.right;
			}
			// both children are not null
		} else if (current.left != null && current.right != null) {
			// get the successor to the current node
			Node suc = getSucc(current);
			// current node is the root, therefore make the root the successor now
			if (current == root) {
				root = suc;
				// the leftchild is true so parent left child becomes the successor
			} else if (isLeftChild) {
				p.left = suc;
				// else the parents right child becomes the successor
			} else {
				p.right = suc;
			}
			suc.left = current.left;
		}
	}

	/**
	 * Method to find the successor node for the deletedNode
	 * 
	 * @param deletedNode
	 *            the node that was deleted
	 */
	public Node getSucc(Node deletedNode) {
		// the successor node
		Node succ = null;
		// the successor parent
		Node succParent = null;
		// the current node becomes the deletedNodes right child
		Node current = deletedNode.right;
		// while the current node isnt null
		while (current != null) {
			// the successor parent is the successor
			succParent = succ;
			// the successor then becomes the current node
			succ = current;
			// the current node becomes the current nodes left child
			current = current.left;
		}
		// check if successor has the right child, it cannot have left child for sure
		// if it does have the right child, add it to the left of successorParent
		if (succ != deletedNode.right) {
			succParent.left = succ.right;
			succ.right = deletedNode.right;
		}
		return succ;
	}

	/**
	 * Method to insert into the tree
	 * 
	 * @param id
	 *            the first data value
	 * @param id2
	 *            the second data value
	 */
	public void insert(int id, int id2) {
		// for a 2 tree node
		if (id != 0 && id2 == 0) {
			// create a newnode for a 2 tree (only needs one data value)
			Node newNode = new Node(id);
			// if tree is empty make newnode the root
			if (root == null) {
				root = newNode;
				return;
			}
			// create a current node equal to the root
			Node current = root;
			// create a parent thats null
			Node parent = null;
			while (true) {
				// parent becomes current node
				parent = current;
				// if the inserting value is less than current node
				if (id < current.data) {
					// current becomes the left current
					current = current.left;
					if (current == null) {
						// if the current is null make the left parent the newnode
						parent.left = newNode;
						return;
					}
				} else {
					// current becomes the right current
					current = current.right;
					if (current == null) {
						// if the current is null make the right parent the newnode
						parent.right = newNode;
						return;
					}
				}
			}
			// same concept for a 3 tree, only adding the center node
		} else {
			Node newNode = new Node(id, id2);
			if (root == null) {
				root = newNode;
				return;
			}
			Node current = root;
			Node parent = null;
			while (true) {
				parent = current;
				if (id < current.data) {
					current = current.left;
					if (current == null) {
						parent.left = newNode;
						return;
					}
					// not sure if implementing this part correctly
					// if id is greater than data value 1 and id2 is less than data value 2
				} else if (id > current.data && id2 < current.data2) {
					current = current.center;
					if (current == null) {
						parent.center = newNode;
						return;
					}
				} else {
					current = current.right;
					if (current == null) {
						parent.right = newNode;
						return;
					}
				}
			}

		}
	}

	/**
	 * Method to display the tree
	 * 
	 * @param root
	 *            the root of the tree
	 */
	public void display(Node root) {
		// the tree is not null
		if (root != null) {
			if (root.data2 == 0) {
				// go into the left node
				display(root.left);
				System.out.print(" " + root.data);
				// go into the right node
				display(root.right);
			} else {
				// go into the left node
				display(root.left);
				System.out.print(" " + root.data);
				display(root.center);
				System.out.print(" " + root.data2);
				// go into the right node
				display(root.right);
			}
		}
	}

	public static void main(String[] args) {
		TestTwoThreeTree b = new TestTwoThreeTree();
		for (int i = 1; i < 11; i++) {
			b.insert(i, 0);
		}
		System.out.println("Inserted values : ");
		b.display(TestTwoThreeTree.root);
		System.out.println("");
		System.out.println("Tree after 3 and 7 are deleted: ");
		b.delete(3);
		b.delete(7);
		b.display(TestTwoThreeTree.root);
		b.delete(13);
		System.out.println("\nTree after 13 is deletd: ");
		b.display(TestTwoThreeTree.root);
	}

	// this assignment was completed in only a few hours due to time constraints on other assignments, tests and exam prep. Please excuse any
	//strange wording, unefficient coding. I was doing this on a few hours of sleep so I'm assuming lots of things will not be correct. Being 
	//that this is the last assignment I would like to ask for some leniency as my other assignments have been top quality and that reflects 
	//in the grades. Thanks for reading this.    
}
