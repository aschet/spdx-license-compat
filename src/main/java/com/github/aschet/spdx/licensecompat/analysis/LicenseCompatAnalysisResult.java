/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.analysis;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.spdx.rdfparser.license.AnyLicenseInfo;

/**
 *
 * @author Thomas Ascher
 *
 */
public class LicenseCompatAnalysisResult {

	/**
	 *
	 */
	private List<AnyLicenseInfo> conflicts = new ArrayList<>();

	/**
	 *
	 */
	private Set<AnyLicenseInfo> licenses = new LinkedHashSet<>();

	/**
	 *
	 */
	private Set<AnyLicenseInfo> unsupportedLicenses = new LinkedHashSet<>();

	/**
	 *
	 * @return
	 */
	public List<AnyLicenseInfo> getConflicts() {
		return conflicts;
	}

	/**
	 *
	 * @return
	 */
	public Set<AnyLicenseInfo> getLicenses() {
		return licenses;
	}

	/**
	 *
	 * @return
	 */
	public Set<AnyLicenseInfo> getUnsupportedLicenses() {
		return unsupportedLicenses;
	}

	/**
	 *
	 * @return
	 */
	public boolean hasConflicts() {
		return getConflicts().size() > 0;
	}

	/**
	 *
	 * @return
	 */
	public boolean hasUnsupportedLicenses() {
		return getUnsupportedLicenses().size() > 0;
	}

	/**
	 *
	 * @param conflicts
	 */
	public void setConflicts(final List<AnyLicenseInfo> conflicts) {
		this.conflicts = conflicts;
	}

	/**
	 *
	 * @param licenses
	 */
	public void setLicenses(final Set<AnyLicenseInfo> licenses) {
		this.licenses = licenses;
	}

	/**
	 *
	 * @param unsupportedLicenses
	 */
	public void setUnsupportedLicenses(final Set<AnyLicenseInfo> unsupportedLicenses) {
		this.unsupportedLicenses = unsupportedLicenses;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("<licenses=");
		builder.append(getLicenses().toString());
		builder.append(", conflicts=");
		builder.append(getConflicts().toString());
		builder.append(", unknown=");
		builder.append(getUnsupportedLicenses().toString());
		builder.append(">");
		return builder.toString();
	}

}