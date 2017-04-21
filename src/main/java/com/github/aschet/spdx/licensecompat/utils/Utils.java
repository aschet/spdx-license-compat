/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.utils;

import java.security.InvalidParameterException;

import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.ExtractedLicenseInfo;

import com.github.aschet.spdx.expression.TypeInfo;

/**
 * Various utility methods.
 *
 * @author Thomas Ascher
 */
public final class Utils {

	/**
	 * 
	 * @param licenseName
	 * @return
	 */
	public static ExtractedLicenseInfo createExtractedLicenseInfo(final String licenseName) {
		return new ExtractedLicenseInfo(licenseName, licenseName, licenseName, null, null);
	}

	/**
	 * Tests if a given parameter is not null and causes an
	 * {@link InvalidParameterException} if null is detected.
	 *
	 * @param parameters
	 *            parameters to test for null
	 */
	public static void ensureNotNull(final Object... parameters) {
		for (final Object parameter : parameters) {
			if (parameter == null) {
				throw new InvalidParameterException("Parameter is not allowed to be of value null.");
			}
		}
	}

	/**
	 * Tests if a given parameter is not a
	 * {@link org.spdx.rdfparser.license.LicenseSet} and causes an
	 * {@link InvalidParameterException} if a set is detected.
	 *
	 * @param expression
	 *            license expression to test
	 */
	public static void ensureNotSet(final AnyLicenseInfo expression) {
		if (TypeInfo.isSet(expression)) {
			throw new InvalidParameterException("License sets are not supported by this operation.");
		}
	}

}