/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph.io;

import org.jgrapht.ext.DOTImporter;

import com.github.aschet.spdx.licensecompat.graph.LicenseEdge;
import com.github.aschet.spdx.licensecompat.graph.LicenseVertex;

/**
 * Imports a license compatibility graph from a DOT file.
 *
 * @author Thomas Ascher
 */
public class LicenseCompatGraphImporter extends DOTImporter<LicenseVertex, LicenseEdge> {

	/**
	 * Configures the importer.
	 */
	public LicenseCompatGraphImporter() {
		super(new LicenseVertexProvider(), new LicenseEdgeProvider());
	}

}