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
public interface LicenseCompatStrategy {

	/**
	 * 
	 * @param sourceLicense
	 * @param targetLicense
	 * @return
	 * @throws UnsupportedLicenseException
	 */
	boolean areCompatible(AnyLicenseInfo sourceLicense, AnyLicenseInfo targetLicense)
			throws UnsupportedLicenseException;

}