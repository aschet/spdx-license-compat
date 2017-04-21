/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph;

import org.jgrapht.EdgeFactory;

/**
 * 
 * @author thomas
 *
 */
class LicenseEdgeFactory implements EdgeFactory<LicenseVertex, LicenseEdge> {

	@Override
	public LicenseEdge createEdge(final LicenseVertex sourceVertex, final LicenseVertex targetVertex) {
		return new LicenseEdge();
	}

}