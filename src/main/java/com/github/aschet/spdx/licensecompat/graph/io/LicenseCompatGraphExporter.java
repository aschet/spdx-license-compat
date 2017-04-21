/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph.io;

import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.IntegerComponentNameProvider;
import org.jgrapht.ext.StringComponentNameProvider;

import com.github.aschet.spdx.licensecompat.graph.LicenseEdge;
import com.github.aschet.spdx.licensecompat.graph.LicenseVertex;

/**
 * 
 * @author Thomas Ascher
 */
public class LicenseCompatGraphExporter extends DOTExporter<LicenseVertex, LicenseEdge> {

	/**
	 * 
	 */
	public LicenseCompatGraphExporter() {
		super(new IntegerComponentNameProvider<>(), new StringComponentNameProvider<>(),
				new StringComponentNameProvider<>());
	}

}