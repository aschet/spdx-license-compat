/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph;

/**
 * The type of an edge which connects two vertices. Edges can be either
 * transitive and non transitive. Non transitive edges will stop a graph
 * traversal at the targed vertex.
 *
 * @author Thomas Ascher
 */
public enum LicenseEdgeType {

	NON_TRANSITIVE("NON_TRANSIVIE"), TRANSITIVE("TRANSITIVE");

	private final String text;

	/**
	 * Construct from the textual enum representation.
	 *
	 * @param text
	 *            textual representation of the edge type
	 */
	private LicenseEdgeType(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}