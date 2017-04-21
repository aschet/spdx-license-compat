/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.analysis;

import org.spdx.rdfparser.license.AnyLicenseInfo;

/**
 * An exception which is triggered when a license is passed to an implementor of
 * {@link LicenseCompatStrategy} which is currently not supported since it is
 * not present in the database of the implementor.
 *
 * @author Thomas Ascher
 */
public class UnsupportedLicenseException extends Exception {

	private static final long serialVersionUID = -2505567098115313356L;

	/**
	 * The unsupported license.
	 */
	private AnyLicenseInfo license;

	/**
	 * Construct with an unsupported license.
	 * 
	 * @param license
	 */
	public UnsupportedLicenseException(final AnyLicenseInfo license) {
		setLicense(license);
	}

	/**
	 * Get the unsupported license.
	 * 
	 * @return the unsupported license
	 */
	public AnyLicenseInfo getLicense() {
		return license;
	}

	/**
	 * Set the unsupported license.
	 * 
	 * @param license
	 *            the unsupported license
	 */
	public void setLicense(final AnyLicenseInfo license) {
		this.license = license;
	}

	@Override
	public String toString() {
		String text = "Unsupported license: ";
		if (getLicense() != null) {
			text += getLicense().toString();
		}
		return text;
	}

}