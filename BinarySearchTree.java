package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T> {
  protected BSTNode<T> root;

  public boolean isEmpty() {
    return root == null;
  }

  public int size() {
    return subtreeSize(root);
  }

  protected int subtreeSize(BSTNode<T> node) {
    if (node == null) {
      return 0;
    } else {
      return 1 + subtreeSize(node.getLeft()) + subtreeSize(node.getRight());
    }
  }
  
  private T getFromTree (T t, BSTNode<T> node) {
    if (node == null) {
	  return null;
	} else if (t.compareTo(node.getData()) < 0) {
	  return getFromTree(t, node.getLeft());
	} else if (t.compareTo(node.getData()) == 0) {
	  return node.getData();
	} else {
	  return getFromTree(t, node.getRight());
	}  
  }

  public boolean contains(T t) {
	if (t == null) {
	  throw new NullPointerException();
	} else if (getFromTree(t, root) == null) {
	  return false;
	}
	return true;
  }

  public boolean remove(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    boolean result = contains(t);
    if (result) {
      root = removeFromSubtree(root, t);
    }
    return result;
  }

  private BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
    // node must not be null
    int result = t.compareTo(node.getData());
    if (result < 0) {
      node.setLeft(removeFromSubtree(node.getLeft(), t));
      return node;
    } else if (result > 0) {
      node.setRight(removeFromSubtree(node.getRight(), t));
      return node;
    } else { // result == 0
      if (node.getLeft() == null) {
        return node.getRight();
      } else if (node.getRight() == null) {
        return node.getLeft();
      } else { // neither child is null
        T predecessorValue = getHighestValue(node.getLeft());
        node.setLeft(removeRightmost(node.getLeft()));
        node.setData(predecessorValue);
        return node;
      }
    }
  }

  private T getHighestValue(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getData();
    } else {
      return getHighestValue(node.getRight());
    }
  }

  private BSTNode<T> removeRightmost(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getLeft();
    } else {
      node.setRight(removeRightmost(node.getRight()));
      return node;
    }
  }

  public T get(T t) {
    // TODO
	if (t == null) {
	  throw new NullPointerException();
    }
    return getFromTree(t, root);
  }

  /**
   * add data into the tree.
   */
  public void add(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    root = addToSubtree(root, new BSTNode<T>(t, null, null));
  }

  protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
    if (node == null) {
      return toAdd;
    }
    int result = toAdd.getData().compareTo(node.getData());
    if (result <= 0) {
      node.setLeft(addToSubtree(node.getLeft(), toAdd));
    } else {
      node.setRight(addToSubtree(node.getRight(), toAdd));
    }
    return node;
  }

  private T leftMinIterator(BSTNode<T> node){
	if (node == null) {
      return null;
	}
	if (node.getLeft() != null) {
	  return leftMinIterator(node.getLeft());
	}
	return node.getData();	
  }
  
  @Override
  public T getMinimum() {
    return leftMinIterator(root);
  }

  private T rightMaxIterator(BSTNode<T> node){
	if (node == null) {
	  return null;
	}
    if (node.getRight() != null) {
	  return rightMaxIterator(node.getRight());
	}
    return node.getData();  
  }
  
  @Override
  public T getMaximum() {
    return rightMaxIterator(root);
  }

  //Height Helper
  private int HH(BSTNode<T> node) {
	if (node == null) {
	  return -1;
	}
	return 1 + Math.max(HH(node.getLeft()), HH(node.getRight()));
  }
  
  @Override
  public int height() {
    return HH(root);
  }

  private void preorderTraverse(Queue<T> queue, BSTNode<T> node) {
	if (node != null) {
	  queue.add(node.getData());	
	  preorderTraverse(queue, node.getLeft());
	  preorderTraverse(queue, node.getRight());
	}
  }
  
  public Iterator<T> preorderIterator() {
	Queue<T> queue = new LinkedList<T>();
	preorderTraverse(queue, root);
	return queue.iterator();
  }

  public Iterator<T> inorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    inorderTraverse(queue, root);
    return queue.iterator();
  }

  private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      inorderTraverse(queue, node.getLeft());
      queue.add(node.getData());
      inorderTraverse(queue, node.getRight());
    }
  }
  
  public Iterator<T> inorderIterator(BSTNode<T> node) {
	Queue<T> queue = new LinkedList<T>();
	inorderTraverse(queue, node);
	return queue.iterator();
  }
  
  private void postorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
	  postorderTraverse(queue, node.getLeft());
	  postorderTraverse(queue, node.getRight());
	  queue.add(node.getData());	
    }
  }  
  
  public Iterator<T> postorderIterator() {
	Queue<T> queue = new LinkedList<T>();
	postorderTraverse(queue, root);
    return queue.iterator();
  }

  //Equals Helper method
  private boolean EH (BSTNode<T> curTreeNode, BSTNode<T> otherTreeNode) {
	if (curTreeNode == null && otherTreeNode == null) {
	  return true;
	}
	if (curTreeNode.getData().compareTo(otherTreeNode.getData()) != 0) {
	  return false;
	}
	boolean leftResult = EH(curTreeNode.getLeft(), otherTreeNode.getLeft());
	boolean rightResult = EH(curTreeNode.getRight(), otherTreeNode.getRight());
	return (leftResult && rightResult);
  }
  
  @Override
  public boolean equals(BSTInterface<T> other) {
    // TODO
	if (this.isEmpty() || other.isEmpty()) {
	  if (this.isEmpty() && other.isEmpty()) {
		return true;
	  }
	  return false;
	}
    return EH(root, other.getRoot());
  }

  @Override
  public boolean sameValues(BSTInterface<T> other) {
	if (other.isEmpty() && this.isEmpty()) {
	  return true;
	}
	if (other.size() != this.size()) {
	  return false;
	}
	  
	Iterator<T> a = this.inorderIterator();
	Iterator<T> b = other.inorderIterator();
	
	while (a.hasNext() && b.hasNext()) {
	  if (a.next() != b.next()) {
		return false;
	  }
	}
    return true;
  }

  @Override
  public boolean isBalanced() {
	boolean greaterThanLowerBound = this.size() >= Math.pow(2, this.height());
	boolean lowerThanUpperBound = this.size() <= Math.pow(2, this.height() + 1);
	if (greaterThanLowerBound && lowerThanUpperBound) {
	  return true;
	}
    return false;
  }

  //balance helper
  private BSTNode<T> sortedArrToBST (T[] arr, int lower, int upper) {	
	if (lower > upper) {
	  return null;
	}
	int mid = (lower + upper) / 2;
	BSTNode<T> node = new BSTNode<T>(arr[mid], null, null);
	node.setLeft(sortedArrToBST(arr, lower, mid - 1));
	node.setRight(sortedArrToBST(arr, mid + 1, upper));
	return node;
  }
  
  @Override
  public void balance() {
    int count = 0;
	T[] sortArr = (T[]) new Comparable[this.size()];
	Iterator<T> a = this.inorderIterator();
	while (a.hasNext()) {
	  sortArr[count++] = a.next();
	}
    root = sortedArrToBST(sortArr, 0, this.size() - 1);
  }


  @Override
  public BSTNode<T> getRoot() {
    // DO NOT MODIFY
    return root;
  }
  
  

  /**
   * toDotFormat.
   * @param root root of tree.
   * @return type T.
   */
  public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
    // header
    int count = 0;
    String dot = "digraph G { \n";
    dot += "graph [ordering=\"out\"]; \n";
    // iterative traversal
    Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
    queue.add(root);
    BSTNode<T> cursor;
    while (!queue.isEmpty()) {
      cursor = queue.remove();
      if (cursor.getLeft() != null) {
        // add edge from cursor to left child
        dot += cursor.getData().toString() + " -> "
            + cursor.getLeft().getData().toString() + ";\n";
        queue.add(cursor.getLeft());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count
            + ";\n";
        count++;
      }
      if (cursor.getRight() != null) {
        // add edge from cursor to right child
        dot += cursor.getData().toString() + " -> "
            + cursor.getRight().getData().toString() + ";\n";
        queue.add(cursor.getRight());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count
            + ";\n";
        count++;
      }

    }
    dot += "};";
    return dot;
  }

  /**
   * main method.
   * @param args arguments.
   */
  public static void main(String[] args) {
    for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
      BSTInterface<String> tree = new BinarySearchTree<String>();
      for (String s : new String[] { "d", "b", "a", "c", "f", "e", "g" }) {
        tree.add(s);
      }
      Iterator<String> iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.preorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.postorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();

      System.out.println(tree.remove(r));

      iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
    }

    BSTInterface<String> tree = new BinarySearchTree<String>();
    for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
      tree.add(r);
    }
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
    tree.balance();
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
  }
}