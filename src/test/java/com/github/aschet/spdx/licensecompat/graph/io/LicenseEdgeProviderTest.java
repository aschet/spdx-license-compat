/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph.io;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.aschet.spdx.licensecompat.graph.LicenseEdgeType;

public class LicenseEdgeProviderTest {

	private LicenseEdgeProvider provider;

	@Before
	public void setUp() {
		provider = new LicenseEdgeProvider();
	}

	private void testBuild(final LicenseEdgeType expectedType) {
		assertEquals(expectedType, provider.buildEdge(null, null, expectedType.toString(), null).getType());
	}

	@Test
	public void testNonTransitiveBuilding() {
		testBuild(LicenseEdgeType.NON_TRANSITIVE);
	}

	@Test
	public void testTransitiveBuilding() {
		testBuild(LicenseEdgeType.TRANSITIVE);
	}

}