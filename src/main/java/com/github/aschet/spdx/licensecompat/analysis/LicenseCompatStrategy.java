/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.analysis;

import org.spdx.rdfparser.license.AnyLicenseInfo;

/**
 * The basic interface for all possible methods which may provide license
 * compatibility information. It allows to test the compatibility between two
 * licenses. License expressions which contain sets are not supported. Forward
 * compatibility is currently not available.
 *
 * @author Thomas Ascher
 */
public interface LicenseCompatStrategy {

	/**
	 * Test the compatibility between two licenses.
	 *
	 * @param sourceLicense
	 *            first license for the compatibility evaluation
	 * @param targetLicense
	 *            second license for the compatibility evaluation
	 * @return true if both licenses are compatible
	 * @throws UnsupportedLicenseException
	 *             is triggered if one of the given licenses is not known by the
	 *             implementor of this interface
	 */
	boolean areCompatible(AnyLicenseInfo sourceLicense, AnyLicenseInfo targetLicense)
			throws UnsupportedLicenseException;

}