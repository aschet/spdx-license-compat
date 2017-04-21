/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph.io;

import java.util.Map;

import org.jgrapht.ext.EdgeProvider;

import com.github.aschet.spdx.licensecompat.graph.LicenseEdge;
import com.github.aschet.spdx.licensecompat.graph.LicenseEdgeType;
import com.github.aschet.spdx.licensecompat.graph.LicenseVertex;

/**
 * 
 * @author Thomas Ascher
 */
final class LicenseEdgeProvider implements EdgeProvider<LicenseVertex, LicenseEdge> {

	@Override
	public LicenseEdge buildEdge(final LicenseVertex from, final LicenseVertex to, final String label,
			final Map<String, String> attributes) {
		final LicenseEdge edge = new LicenseEdge();
		if (label.equals(LicenseEdgeType.NON_TRANSITIVE.toString())) {
			edge.setType(LicenseEdgeType.NON_TRANSITIVE);
		}
		return edge;
	}

}