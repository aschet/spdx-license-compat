/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.jgrapht.ext.ExportException;
import org.jgrapht.ext.ImportException;
import org.junit.Before;
import org.junit.Test;
import org.spdx.rdfparser.license.LicenseInfoFactory;
import org.spdx.spdxspreadsheet.InvalidLicenseStringException;

import com.github.aschet.spdx.licensecompat.graph.LicenseCompatGraph;
import com.github.aschet.spdx.licensecompat.graph.LicenseVertex;

public class LicenseCompatGraphImporterTest {

	private ByteArrayInputStream data;

	private LicenseCompatGraph expectedGraph;

	@Before
	public void setUp() throws InvalidLicenseStringException, ExportException {
		expectedGraph = new LicenseCompatGraph();
		final LicenseVertex LGPL21 = new LicenseVertex(LicenseInfoFactory.parseSPDXLicenseString("LGPL-2.1+"));
		final LicenseVertex LGPL3 = new LicenseVertex(LicenseInfoFactory.parseSPDXLicenseString("LGPL-3.0+"));
		final LicenseVertex GPL3 = new LicenseVertex(LicenseInfoFactory.parseSPDXLicenseString("GPL-3.0+"));

		expectedGraph.addVertex(LGPL21);
		expectedGraph.addVertex(LGPL3);
		expectedGraph.addVertex(GPL3);
		expectedGraph.addEdge(LGPL21, LGPL3);
		expectedGraph.addEdge(LGPL3, GPL3);

		final LicenseCompatGraphExporter exporter = new LicenseCompatGraphExporter();
		final ByteArrayOutputStream output = new ByteArrayOutputStream();
		exporter.exportGraph(expectedGraph, output);
		data = new ByteArrayInputStream(output.toByteArray());
	}

	@Test
	public void testGraphExportImport() throws ImportException {
		final LicenseCompatGraph graph = new LicenseCompatGraph();
		final LicenseCompatGraphImporter importer = new LicenseCompatGraphImporter();

		importer.importGraph(graph, data);

		assertEquals(expectedGraph.toString(), graph.toString());
	}

}