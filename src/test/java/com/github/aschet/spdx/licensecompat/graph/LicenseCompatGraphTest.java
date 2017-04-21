/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.ExtractedLicenseInfo;
import org.spdx.rdfparser.license.LicenseInfoFactory;
import org.spdx.spdxspreadsheet.InvalidLicenseStringException;

import com.github.aschet.spdx.licensecompat.analysis.UnsupportedLicenseException;
import com.github.aschet.spdx.licensecompat.utils.Utils;

public class LicenseCompatGraphTest {

	private LicenseVertex APACHE2;

	private LicenseVertex GPL2;

	private LicenseCompatGraph graph;

	private LicenseVertex LGPL2;

	private LicenseVertex LGPL3;

	private LicenseVertex MPL2;

	private LicenseVertex addVertex(final String license) throws InvalidLicenseStringException {
		final LicenseVertex vertex = new LicenseVertex(LicenseInfoFactory.parseSPDXLicenseString(license));
		graph.addVertex(vertex);
		return vertex;
	}

	@Before
	public void setUp() throws InvalidLicenseStringException {
		graph = new LicenseCompatGraph();

		APACHE2 = addVertex("Apache-2.0");
		MPL2 = addVertex("MPL-2.0");
		LGPL2 = addVertex("LGPL-2.1+");
		LGPL3 = addVertex("LGPL-3.0");
		GPL2 = addVertex("GPL-2.0");

		graph.addEdge(APACHE2, MPL2);
		graph.getEdge(APACHE2, MPL2).setType(LicenseEdgeType.NON_TRANSITIVE);
		graph.addEdge(MPL2, LGPL2);
		graph.addEdge(MPL2, GPL2);
		graph.addEdge(LGPL2, GPL2);
		graph.addEdge(LGPL2, LGPL3);
	}

	@Test
	public void testCompatibility() throws UnsupportedLicenseException {
		assertTrue(graph.areCompatible(GPL2.getLicenses(), GPL2.getLicenses()));
		assertTrue(graph.areCompatible(MPL2.getLicenses(), GPL2.getLicenses()));
		assertFalse(graph.areCompatible(APACHE2.getLicenses(), GPL2.getLicenses()));
	}

	@Test
	public void testNonSPDXLicenseHandling() throws UnsupportedLicenseException {
		final ExtractedLicenseInfo customLicense = Utils.createExtractedLicenseInfo("Custom License");
		final LicenseVertex customVertex = new LicenseVertex(customLicense.clone());
		graph.addVertex(customVertex);
		graph.addEdge(LGPL3, customVertex);
		customLicense.setLicenseId("LicenseRef-Custom");

		assertTrue(graph.areCompatible(customLicense, LGPL2.getLicenses()));
	}

	@Test
	public void testNonTransitiveTraversal() {
		testTraversal(APACHE2, APACHE2.getLicenses(), MPL2.getLicenses());
	}

	@Test
	public void testTransitiveTraversal() {
		testTraversal(LGPL2, LGPL2.getLicenses(), LGPL3.getLicenses(), GPL2.getLicenses());
	}

	private void testTraversal(final LicenseVertex startVertex, final AnyLicenseInfo... expectedLicenses) {
		final Set<AnyLicenseInfo> licenses = graph.traverse(startVertex);
		assertEquals(expectedLicenses.length, licenses.size());
		for (final AnyLicenseInfo expectedLicense : expectedLicenses) {
			assertTrue(expectedLicense.toString(), licenses.contains(expectedLicense));
		}
	}

	@Test(expected = UnsupportedLicenseException.class)
	public void testUnsupportedLicenseHandling() throws UnsupportedLicenseException, InvalidLicenseStringException {
		graph.areCompatible(LicenseInfoFactory.parseSPDXLicenseString("MIT"), GPL2.getLicenses());
	}

	@Test
	public void testVertexFinding() throws InvalidLicenseStringException {
		assertEquals(LGPL2, graph.findVertex(LGPL2.getLicenses()));
		assertEquals(null, graph.findVertex(LicenseInfoFactory.parseSPDXLicenseString("GPL-3.0")));
	}
}