/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.spdx.rdfparser.InvalidSPDXAnalysisException;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.ExtractedLicenseInfo;
import org.spdx.rdfparser.license.LicenseInfoFactory;
import org.spdx.spdxspreadsheet.InvalidLicenseStringException;

import com.github.aschet.spdx.expression.ExpressionMerging;
import com.github.aschet.spdx.licensecompat.utils.Utils;

public class LicenseVertexProviderTest {

	private LicenseVertexProvider provider;

	private Set<AnyLicenseInfo> build(final String license) {
		final Map<String, String> attributes = new HashMap<>();
		attributes.put("label", license);
		return provider.buildVertex("1", attributes).getLicenseSet();
	}

	@Before
	public void setUp() {
		provider = new LicenseVertexProvider();
	}

	@Test
	public void testNonSPDXLicenseBuilding() throws InvalidSPDXAnalysisException {
		final ExtractedLicenseInfo expectedLicense = Utils.createExtractedLicenseInfo("Custom License");
		final Set<AnyLicenseInfo> licenses = build(expectedLicense.getName());

		assertEquals(1, licenses.size());
		assertTrue(licenses.contains(expectedLicense));
	}

	@Test
	public void testSPDXLicenseBuilding() throws InvalidLicenseStringException {
		final AnyLicenseInfo GPL = LicenseInfoFactory.parseSPDXLicenseString("GPL-2.0");
		final AnyLicenseInfo MIT = LicenseInfoFactory.parseSPDXLicenseString("MIT");

		final Set<AnyLicenseInfo> licenses = build(ExpressionMerging.orJoin(GPL, MIT).toString());

		assertEquals(2, licenses.size());
		assertTrue(licenses.contains(GPL));
		assertTrue(licenses.contains(MIT));
	}

}