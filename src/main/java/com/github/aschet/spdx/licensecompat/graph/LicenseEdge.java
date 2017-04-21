/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph;

/**
 * 
 * @author thomas
 *
 */
public class LicenseEdge {

	/**
	 * 
	 */
	private LicenseEdgeType type = LicenseEdgeType.TRANSITIVE;

	/**
	 * 
	 * @return
	 */
	public LicenseEdgeType getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 */
	public void setType(final LicenseEdgeType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type.toString();
	}

}