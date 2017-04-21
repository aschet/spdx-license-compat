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
 * The result of the license compatibility evaluation performed by
 * {@link LicenseCompatAnalysis}. A result contains the licenses which were part
 * of the compatibility test, the conflicts which resulted from the
 * compatibility test and the licenses which were not recognized by the
 * {@link LicenseCompatStrategy}.
 *
 * @author Thomas Ascher
 */
public class LicenseCompatAnalysisResult {

	/**
	 * List of conflicts between the tested licenses.
	 */
	private List<AnyLicenseInfo> conflicts = new ArrayList<>();

	/**
	 * Set of the tested licenses.
	 */
	private Set<AnyLicenseInfo> licenses = new LinkedHashSet<>();

	/**
	 * Set of the tested licenses which were not supported.
	 */
	private Set<AnyLicenseInfo> unsupportedLicenses = new LinkedHashSet<>();

	/**
	 * Get the conflicts between the tested licenses.
	 * 
	 * @return conflicts as list of conjunctive SPDX expressions
	 */
	public List<AnyLicenseInfo> getConflicts() {
		return conflicts;
	}

	/**
	 * Get the tested licenses.
	 * 
	 * @return licenses as SPDX identifiers
	 */
	public Set<AnyLicenseInfo> getLicenses() {
		return licenses;
	}

	/**
	 * Get the unrecognized licenses.
	 *
	 * @return licenses as SPDX identifiers
	 */
	public Set<AnyLicenseInfo> getUnsupportedLicenses() {
		return unsupportedLicenses;
	}

	/**
	 * Determines if the result has conflicts.
	 *
	 * @return true if the result has conflicts
	 */
	public boolean hasConflicts() {
		return getConflicts().size() > 0;
	}

	/**
	 * Determines if some of the tested licenses where not supported.
	 *
	 * @return true when unsupported licenses are present
	 */
	public boolean hasUnsupportedLicenses() {
		return getUnsupportedLicenses().size() > 0;
	}

	/**
	 * Set the conflicts between the tested licenses.
	 *
	 * @param conflicts
	 *            the conflicts between the licenses as conjunctive sets
	 */
	public void setConflicts(final List<AnyLicenseInfo> conflicts) {
		this.conflicts = conflicts;
	}

	/**
	 * Set the tested licenses.
	 *
	 * @param licenses
	 *            licenses as SPDX identifiers
	 */
	public void setLicenses(final Set<AnyLicenseInfo> licenses) {
		this.licenses = licenses;
	}

	/**
	 * Set the unrecognized licenses.
	 *
	 * @param unsupportedLicenses
	 *            licenses as SPDX identifiers
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