/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph;

import java.util.HashSet;
import java.util.Set;

import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.ExtractedLicenseInfo;
import org.spdx.rdfparser.license.SpdxNoneLicense;

import com.github.aschet.spdx.expression.ExpressionSplitting;
import com.github.aschet.spdx.licensecompat.utils.Utils;

/**
 * Part of the JGraphT interface. Represents a set of licenses. As input all
 * SPDX expressions are supported which contain only SPDX license identifiers.
 * Alternatively a vertex can also contain one custom license name as
 * represented in an {@link ExtractedLicenseInfo} instance.
 *
 * @author Thomas Ascher
 */
public class LicenseVertex {

	/**
	 * License expression of the vertex.
	 */
	private AnyLicenseInfo licenses = new SpdxNoneLicense();

	/**
	 * Extracted licenses from the contained license expression.
	 */
	private Set<AnyLicenseInfo> licenseSet = new HashSet<>();

	/**
	 * Construct without contained licenses.
	 */
	public LicenseVertex() {
	}

	/**
	 * Construct from an SPDX license expression or a custom license name. And
	 * and or operaters are ignored.
	 *
	 * @param licenses
	 *            a SPDX license expression or a custom license name
	 */
	public LicenseVertex(final AnyLicenseInfo licenses) {
		setLicenses(licenses);
	}

	/**
	 * Tests if the vertex contains the given license.
	 *
	 * @param license
	 *            license to test
	 * @return true if the vertex contains the given license
	 */
	public boolean contains(final AnyLicenseInfo license) {
		Utils.ensureNotNull(license);
		return getLicenseSet().contains(license);
	}

	/**
	 * Get the internal license expression.
	 *
	 * @return a SPDX license expression
	 */
	public AnyLicenseInfo getLicenses() {
		return licenses;
	}

	/**
	 * Get the set of licenses derived from the internal license epxression.
	 *
	 * @return a set of licenses
	 */
	public Set<AnyLicenseInfo> getLicenseSet() {
		return licenseSet;
	}

	/**
	 * Set the internal license expression.
	 *
	 * @param licenses
	 *            a SPDX license expression
	 */
	public void setLicenses(final AnyLicenseInfo licenses) {
		this.licenses = licenses;
		licenseSet = ExpressionSplitting.splitToLicenses(licenses);
	}

	@Override
	public String toString() {
		return licenses.toString();
	}

}