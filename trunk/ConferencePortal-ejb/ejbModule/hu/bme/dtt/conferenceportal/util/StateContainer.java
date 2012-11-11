/**
 * 
 */
package hu.bme.dtt.conferenceportal.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds and manages a generic type selection list.
 * 
 * @param <T>
 *            - the type of list items
 * @author Windisch.Karoly
 */
public class StateContainer<T> {

	/**
	 * The list we manipulate. Contains generic type items.
	 */
	private List<T> list = new ArrayList<T>();

	/**
	 * @param list
	 */
	public StateContainer(List<T> list) {
		super();
		this.list = list;
	}

	/**
	 * @param list
	 */
	public StateContainer() {
		super();
	}

	/**
	 * Changes the selection of the given item (if it was selected before, than
	 * clears the selection, if it was not selected, than selects.
	 * 
	 * @param item
	 *            - which selection we want to change.
	 */
	public void changeItemSelection(final T item) {

		if (list.contains(item)) {
			list.remove(item);
		} else {
			list.add(item);
		}
	}

	/**
	 * Gets the current list.
	 * 
	 * @return the current list.
	 */
	public List<T> getList() {
		return this.list;
	}

	/**
	 * Sets the list.
	 * 
	 * @param list
	 *            - the list we manipulate.
	 */
	public void setList(final List<T> list) {
		this.list = list;
	}

	/**
	 * Törli a lista elemeit.
	 */
	public void clear() {
		list.clear();
	}

}
