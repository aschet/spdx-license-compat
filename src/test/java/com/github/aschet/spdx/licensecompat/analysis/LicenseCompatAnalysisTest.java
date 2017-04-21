/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.analysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.jgrapht.ext.ImportException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseInfoFactory;
import org.spdx.spdxspreadsheet.InvalidLicenseStringException;

import com.github.aschet.spdx.licensecompat.graph.LicenseCompatGraph;

public class LicenseCompatAnalysisTest {

	private static LicenseCompatStrategy compatStrategy;

	@BeforeClass
	public static void setUpBeforeClass() throws ImportException {
		compatStrategy = LicenseCompatGraph.createFromResources();
	}

	private LicenseCompatAnalysis analysis;

	@Before
	public void setUp() throws ImportException {
		analysis = new LicenseCompatAnalysis(compatStrategy);
	}

	@Test
	public void testAnalyse() throws InvalidLicenseStringException {

		final AnyLicenseInfo[] licensesFromFiles = new AnyLicenseInfo[3];
		licensesFromFiles[0] = LicenseInfoFactory.parseSPDXLicenseString("GPL-3.0");
		licensesFromFiles[1] = LicenseInfoFactory.parseSPDXLicenseString("LGPL-3.0+");
		licensesFromFiles[2] = LicenseInfoFactory.parseSPDXLicenseString("MIT");

		final Map<AnyLicenseInfo, List<LicenseCompatAnalysisResult>> results = analysis
				.analyse(LicenseInfoFactory.parseSPDXLicenseString("(AGPL-3.0 OR GPL-3.0)"), licensesFromFiles

		);

		assertEquals(2, results.size());
		assertTrue(results.containsKey(licensesFromFiles[0]));
		assertTrue(results.containsKey(LicenseInfoFactory.parseSPDXLicenseString("AGPL-3.0")));
	}

	@Test
	public void testAnalyseExpressions() throws InvalidLicenseStringException {

		final List<LicenseCompatAnalysisResult> results = analysis.analyseExpressions(
				LicenseInfoFactory.parseSPDXLicenseString("(((LGPL-3.0+ OR MIT) AND GPL-2.0) OR BSD-3-Clause)"));

		assertEquals(3, results.size());

		final LicenseCompatAnalysisResult result1 = results.get(0);
		assertEquals("[BSD-3-Clause]", result1.getLicenses().toString());
		assertTrue(result1.getConflicts().isEmpty());
		assertTrue(result1.getUnsupportedLicenses().isEmpty());

		final LicenseCompatAnalysisResult result2 = results.get(1);
		assertEquals("[LGPL-3.0+, GPL-2.0]", result2.getLicenses().toString());
		assertEquals("[(LGPL-3.0+ AND GPL-2.0)]", result2.getConflicts().toString());
		assertTrue(result2.getUnsupportedLicenses().isEmpty());

		final LicenseCompatAnalysisResult result3 = results.get(2);
		assertEquals("[MIT, GPL-2.0]", result3.getLicenses().toString());
		assertTrue(result3.getConflicts().isEmpty());
		assertTrue(result3.getUnsupportedLicenses().isEmpty());
	}

}