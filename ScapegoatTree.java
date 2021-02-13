package structures;

import java.util.Iterator;

public class ScapegoatTree<T extends Comparable<T>> extends BinarySearchTree<T> {
  private int upperBound;

  private double log32(int i) {
	return (Math.log(i) / Math.log(3.0/2.0));
  }
  
  @Override
  public void add(T t) {
	BSTNode<T> node = new BSTNode<T>(t, null, null);
	root = addToSubtree(root, node);
	upperBound++;
	
	if (this.height() > this.log32(upperBound)) {
	  node = node.getParent();
	  while (3 * this.subtreeSize(node) <= 2 * this.subtreeSize(node.getParent())) {
		node = node.getParent();
	  }
	  node = node.getParent();
	  BSTNode<T> parent = node.getParent();
	  
	  BinarySearchTree<T> tempTree = new BinarySearchTree<T>();
	  Iterator<T> a = this.inorderIterator(node);
	  while(a.hasNext()) {
		tempTree.add(a.next());
	  }
	  
	  tempTree.balance();
	  
	  if (parent.getRight() == node) {
		parent.setRight(tempTree.getRoot());
	  } else {
		parent.setLeft(tempTree.getRoot());
	  }  
	}
  }

  @Override
  public boolean remove(T element) {
	if (super.remove(element)) {
	  if (2 * this.size() < upperBound) {
		this.balance();
		  upperBound = this.size();
		}
	  return true;
	}
    return false;
  }
}
