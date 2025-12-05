package org.legoata.decision.node.branching;

/**
 * The level of specificity required when checking an input against keys. This is utilized by the Menu in this same package.
 */
public enum InputSpecificity {
	/**
	 * User input must be exact match to key
	 */
	EXACT_MATCH,
	/**
	 * User input must match all characters in key, but case is ignored
	 */
	CASE_INSENSITIVE,
	/**
	 * User input is matched to the key that contains most matching characters, ignoring case, and starting at index 0
	 */
	CLOSEST_MATCH
}
