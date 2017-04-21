/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.analysis;

import org.spdx.rdfparser.license.AnyLicenseInfo;

/**
 * 
 * @author Thomas Ascher
 */
public class UnsupportedLicenseException extends Exception {

	private static final long serialVersionUID = -2505567098115313356L;

	/**
	 * 
	 */
	private AnyLicenseInfo license;

	/**
	 * 
	 * @param license
	 */
	public UnsupportedLicenseException(final AnyLicenseInfo license) {
		setLicense(license);
	}

	/**
	 * 
	 * @return
	 */
	public AnyLicenseInfo getLicense() {
		return license;
	}

	/**
	 * 
	 * @param license
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