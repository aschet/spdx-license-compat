/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph;

/**
 * Part of the JGraphT interface. Determines the type of an edge that connects
 * two vertices. See also {@link LicenseEdgeType}
 *
 * @author Thomas Ascher
 */
public class LicenseEdge {

	/**
	 * Type of the edge.
	 */
	private LicenseEdgeType type = LicenseEdgeType.TRANSITIVE;

	/**
	 * Queries the current edge type
	 *
	 * @return the current edge type
	 */
	public LicenseEdgeType getType() {
		return type;
	}

	/**
	 * Change the type of the edge
	 *
	 * @param type
	 *            new edge type
	 */
	public void setType(final LicenseEdgeType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type.toString();
	}

}