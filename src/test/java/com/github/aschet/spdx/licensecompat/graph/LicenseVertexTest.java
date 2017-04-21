/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseInfoFactory;
import org.spdx.spdxspreadsheet.InvalidLicenseStringException;

import com.github.aschet.spdx.expression.ExpressionMerging;

public class LicenseVertexTest {

	private AnyLicenseInfo GPL;

	private AnyLicenseInfo LGPL;

	private AnyLicenseInfo MIT;

	private LicenseVertex vertex;

	@Before
	public void setUp() throws InvalidLicenseStringException {
		LGPL = LicenseInfoFactory.parseSPDXLicenseString("LGPL-2.1+");
		GPL = LicenseInfoFactory.parseSPDXLicenseString("GPL-2.0+");
		MIT = LicenseInfoFactory.parseSPDXLicenseString("MIT");
		vertex = new LicenseVertex(ExpressionMerging.orJoin(GPL, MIT));
	}

	@Test
	public void testContains() {
		assertFalse(vertex.contains(LGPL));
		assertTrue(vertex.contains(GPL));
		assertTrue(vertex.contains(MIT));
	}

}