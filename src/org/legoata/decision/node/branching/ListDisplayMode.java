package org.legoata.decision.node.branching;

/**
 * Display modes for the Menu's option list.
 */
public enum ListDisplayMode {
	/**
	 * Display Option keys and titles, separated by the separator
	 */
	KEYS_AND_TITLES,
	/**
	 * Display Option keys only
	 */
	KEYS_ONLY,
	/**
	 * Have Menu set the option keys by numbering them, starting at 0; key and title separated by separator
	 */
	NUMBER_FROM_ZERO,
	/**
	 * Have Menu set the option keys by numbering them, starting at 1; key and title separated by separator
	 */
	NUMBER_FROM_ONE
}
