package com.game.environment;

/**
 * 
 * @author TBworkstation
 * 
 * @param <T>
 */
public class SearchState<T> {

  /**
   * creates a new search state object with the specified node, depth, and
   * parent
   * 
   * @param depth
   *          the depth of the node
   * @param node
   *          the node itself
   * @param parent
   *          the parent the node came from
   */
	public SearchState(int depth, T node, SearchState<T> parent) {
		_depth = depth;
		_node = node;
		_parent = parent;
	}
	
  /**
   * Gets the integer depth of the node
   * 
   * @return the integer depth of the node
   */
	public int getDepth() {
		return _depth;
	}
	
  /**
   * Gets the node this {@link SearchState} object is tied to
   * 
   * @return the node this {@link SearchState} object is tied to
   */
	public T getNode() {
		return _node;
	}
	
  /**
   * returns the parent {@link SearchState} object this node came from
   * 
   * @return the parent {@link SearchState} object this node came from
   */
	public SearchState<T> getParent() {
		return _parent;
	}
	
	
	/* ====================== private parts ========================= */
	private final int _depth;
	private final T _node;
	private final SearchState<T> _parent;
}
