/**
 * 
 */
package hu.bme.dtt.conferenceportal.util;

/**
 * Template class for holding a selected item for example in conversation.
 * 
 * @author winkari5
 * @param <ItemClass>
 *            - template parameter
 */
public class StateHolder<ItemClass> {

	/**
	 * The actual selected item.
	 */
	protected ItemClass selected;

	/**
	 * Constructor.
	 */
	public StateHolder() {
	}

	/**
	 * @param selected
	 *            - default selected value
	 */
	public StateHolder(final ItemClass selected) {
		super();
		this.selected = selected;
	}

	/**
	 * @return the selected
	 */
	public ItemClass getSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(final ItemClass selected) {
		this.selected = selected;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StateHolder [selected=" + selected + "]";
	}
}
