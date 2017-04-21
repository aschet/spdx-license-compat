/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph;

/**
 * 
 * @author Thomas Ascher
 */
public enum LicenseEdgeType {

	NON_TRANSITIVE("NON_TRANSIVIE"), TRANSITIVE("TRANSITIVE");

	/**
	 * 
	 */
	private final String text;

	/**
	 * 
	 * @param text
	 */
	private LicenseEdgeType(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}