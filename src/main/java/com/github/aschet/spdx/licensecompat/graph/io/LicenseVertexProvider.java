/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph.io;

import java.util.Map;

import org.jgrapht.ext.VertexProvider;
import org.spdx.rdfparser.license.LicenseInfoFactory;
import org.spdx.spdxspreadsheet.InvalidLicenseStringException;

import com.github.aschet.spdx.licensecompat.graph.LicenseVertex;
import com.github.aschet.spdx.licensecompat.utils.Utils;

/**
 * 
 * @author Thomas Ascher
 */
final class LicenseVertexProvider implements VertexProvider<LicenseVertex> {

	@Override
	public LicenseVertex buildVertex(final String label, final Map<String, String> attributes) {
		final LicenseVertex vertex = new LicenseVertex();
		final String licenseID = attributes.get("label");

		try {
			vertex.setLicenses(LicenseInfoFactory.parseSPDXLicenseString(licenseID));
		} catch (final InvalidLicenseStringException e) {
			vertex.setLicenses(Utils.createExtractedLicenseInfo(licenseID));
		}

		return vertex;
	}

}