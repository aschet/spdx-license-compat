/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.graph;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.jgrapht.ext.ImportException;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.ExtractedLicenseInfo;

import com.github.aschet.spdx.expression.ExpressionFiltering;
import com.github.aschet.spdx.expression.ExpressionFiltering.OperatorFilter;
import com.github.aschet.spdx.licensecompat.analysis.LicenseCompatStrategy;
import com.github.aschet.spdx.licensecompat.analysis.UnsupportedLicenseException;
import com.github.aschet.spdx.licensecompat.graph.io.LicenseCompatGraphImporter;
import com.github.aschet.spdx.licensecompat.utils.Utils;

/**
 * This class represents a license compatibility graph that can determine the
 * compatibility between licenses based on SPDX license identifiers. Each vertex
 * in the graph can consist of multiple SPDX license identifiers or a custom
 * license name. For further details see Kapitsaki, Kramer and Tselikas (2016):
 * Automating the License Compatibility Process in Open Source Software with
 * SPDX.
 *
 * @author Thomas Ascher
 */
public class LicenseCompatGraph extends DefaultDirectedGraph<LicenseVertex, LicenseEdge>
		implements LicenseCompatStrategy {

	private static final long serialVersionUID = -2023214205631374559L;

	/**
	 * Creates a graph from a DOT file.
	 *
	 * @param file
	 *            path to load graph data from
	 * @return the reconstructed graph
	 * @throws ImportException
	 */
	public static LicenseCompatGraph createFromFile(final File file) throws ImportException {
		final LicenseCompatGraph graph = new LicenseCompatGraph();
		final LicenseCompatGraphImporter importer = new LicenseCompatGraphImporter();

		importer.importGraph(graph, file);
		return graph;
	}

	/**
	 * Creates a graph from the DOT file that is stored in the internal
	 * resources
	 *
	 * @return the reconstructed graph
	 * @throws ImportException
	 */
	public static LicenseCompatGraph createFromResources() throws ImportException {
		final LicenseCompatGraph graph = new LicenseCompatGraph();
		final LicenseCompatGraphImporter importer = new LicenseCompatGraphImporter();

		final InputStream input = ClassLoader.class.getResourceAsStream("/LicenseCompatGraph.dot");
		importer.importGraph(graph, input);
		return graph;
	}

	/**
	 * Creates an empty license compatibility graph.
	 */
	public LicenseCompatGraph() {
		super(new LicenseEdgeFactory());
	}

	@Override
	public boolean areCompatible(final AnyLicenseInfo sourceLicense, final AnyLicenseInfo targetLicense)
			throws UnsupportedLicenseException {

		Utils.ensureNotNull(sourceLicense, targetLicense);
		Utils.ensureNotSet(sourceLicense);
		Utils.ensureNotSet(targetLicense);

		final AnyLicenseInfo filteredSourceLicense = filterLicense(sourceLicense);
		final AnyLicenseInfo filteredTargetLicense = filterLicense(targetLicense);

		if (filteredSourceLicense.equals(filteredTargetLicense)) {
			return true;
		}

		final LicenseVertex sourceVertex = findVertex(filteredSourceLicense);
		if (sourceVertex == null) {
			throw new UnsupportedLicenseException(sourceLicense);
		}

		final LicenseVertex targetVertex = findVertex(filteredTargetLicense);
		if (targetVertex == null) {
			throw new UnsupportedLicenseException(targetLicense);
		}

		return traverse(sourceVertex).contains(filteredTargetLicense)
				|| traverse(targetVertex).contains(filteredSourceLicense);
	}

	/**
	 * Modifies the given license for lookup in the graph. Currently license
	 * exceptions are not supported. Furthermore only extracted licenses with a
	 * name will be processed.
	 *
	 * @param license
	 *            the license that should be prepared for lookup
	 * @return the license which can be used for lookup
	 * @throws UnsupportedLicenseException
	 *             the given extracted license has no name
	 */
	AnyLicenseInfo filterLicense(final AnyLicenseInfo license) throws UnsupportedLicenseException {
		final AnyLicenseInfo targetLicense = ExpressionFiltering.filterOperators(license, OperatorFilter.FILTER_ALL);
		if (targetLicense instanceof ExtractedLicenseInfo) {
			final ExtractedLicenseInfo extractedLicense = (ExtractedLicenseInfo) targetLicense;
			if (extractedLicense.getName() == null) {
				throw new UnsupportedLicenseException(extractedLicense);
			} else {
				return Utils.createExtractedLicenseInfo(extractedLicense.getName());
			}
		} else {
			return ExpressionFiltering.filterOperators(license, OperatorFilter.FILTER_WITH_EXCEPTION);
		}

	}

	/**
	 * Search for the vertex which is associated with the given license.
	 *
	 * @param license
	 *            the license to lookup
	 * @return the vertex which contains the given license or null
	 */
	LicenseVertex findVertex(final AnyLicenseInfo license) {
		for (final LicenseVertex vertex : vertexSet()) {
			if (vertex.contains(license)) {
				return vertex;
			}
		}

		return null;
	}

	/**
	 * Traverses all outgoing vertices from a start vertex and collect the
	 * licenses contained in these vertices. Edges between vertices may be
	 * transitive or non transitive. The traversal for non transitive edges
	 * stops at the target vertex of the specific edge. The returned licenses
	 * are forward compatible with the licenses of the start vertex.
	 *
	 * @param startVertex
	 *            the vertex to start the traversal from
	 * @return the list of licenses as visited by the traversal
	 */
	Set<AnyLicenseInfo> traverse(final LicenseVertex startVertex) {

		final Set<LicenseVertex> traversedVertices = new LinkedHashSet<>();
		final Set<AnyLicenseInfo> licenses = new LinkedHashSet<>();

		final Queue<LicenseVertex> traverseQueue = new LinkedList<>();
		traverseQueue.add(startVertex);
		licenses.addAll(startVertex.getLicenseSet());
		traversedVertices.add(startVertex);

		while (!traverseQueue.isEmpty()) {
			final LicenseVertex vertex = traverseQueue.remove();
			for (final LicenseEdge edge : outgoingEdgesOf(vertex)) {
				final LicenseVertex targetVertex = getEdgeTarget(edge);

				licenses.addAll(targetVertex.getLicenseSet());

				if (edge.getType() == LicenseEdgeType.TRANSITIVE) {
					if (!traversedVertices.contains(targetVertex)) {
						traversedVertices.add(targetVertex);
						traverseQueue.add(targetVertex);
					}
				}
			}
		}

		return licenses;
	}

}