/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph.io;

import org.jgrapht.ext.DOTImporter;

import com.github.aschet.spdx.licensecompat.graph.LicenseEdge;
import com.github.aschet.spdx.licensecompat.graph.LicenseVertex;

/**
 * 
 * @author Thomas Ascher
 *
 */
public class LicenseCompatGraphImporter extends DOTImporter<LicenseVertex, LicenseEdge> {

	/**
	 * 
	 */
	public LicenseCompatGraphImporter() {
		super(new LicenseVertexProvider(), new LicenseEdgeProvider());
	}

}