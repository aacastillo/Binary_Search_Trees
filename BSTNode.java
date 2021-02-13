package structures;

public class BSTNode<T extends Comparable<T>> implements BSTNodeInterface<T> {
  private T data;
  private BSTNode<T> left;
  private BSTNode<T> right;
  private BSTNode<T> mother;

  public BSTNode(T data, BSTNode<T> left, BSTNode<T> right) {
    this.data = data;
    this.left = left;
    this.right = right;
  }

  public BSTNode<T> getParent() {
	return mother;
  }
  
  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public BSTNode<T> getLeft() {
    return left;
  }

  public void setLeft(BSTNode<T> left) {
    this.left = left;
    if (this.left != null) {
      left.mother = this;
    }
  }

  public BSTNode<T> getRight() {
    return right;
  }

  public void setRight(BSTNode<T> right) {
    this.right = right;
    if (this.right != null) {
      right.mother = this;
    }
  }
}