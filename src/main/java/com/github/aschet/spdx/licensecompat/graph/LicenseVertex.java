/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph;

import java.util.HashSet;
import java.util.Set;

import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.SpdxNoneLicense;

import com.github.aschet.spdx.expression.ExpressionSplitting;

/**
 * 
 * @author Thomas Ascher
 */
public class LicenseVertex {

	/**
	 * 
	 */
	private AnyLicenseInfo licenses = new SpdxNoneLicense();

	/**
	 * 
	 */
	private Set<AnyLicenseInfo> licenseSet = new HashSet<>();

	/**
	 * 
	 */
	public LicenseVertex() {
	}

	/**
	 * 
	 * @param licenses
	 */
	public LicenseVertex(final AnyLicenseInfo licenses) {
		setLicenses(licenses);
	}

	/**
	 * 
	 * @param license
	 * @return
	 */
	public boolean contains(final AnyLicenseInfo license) {
		return getLicenseSet().contains(license);
	}

	/**
	 * 
	 * @return
	 */
	public AnyLicenseInfo getLicenses() {
		return licenses;
	}

	/**
	 * 
	 * @return
	 */
	public Set<AnyLicenseInfo> getLicenseSet() {
		return licenseSet;
	}

	/**
	 * 
	 * @param licenses
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