/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: LGPL-3.0+
 */

package com.github.aschet.spdx.licensecompat.analysis;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.ext.ImportException;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.SpdxNoneLicense;

import com.github.aschet.spdx.expression.ExpressionMerging;
import com.github.aschet.spdx.expression.ExpressionSplitting;
import com.github.aschet.spdx.expression.TypeInfo;
import com.github.aschet.spdx.licensecompat.graph.LicenseCompatGraph;
import com.github.aschet.spdx.licensecompat.utils.Utils;

/**
 * Performs a license compatibility analysis on a set of SPDX license
 * expressions (with support for dual and multi licensing). This class can be
 * used to implement license compliance systems which analyze the contents of
 * SPDX documents. Basis for the analysis implementation is a implementor of
 * {@link LicenseCompatStrategy}.
 *
 * @author Thomas Ascher
 */
public class LicenseCompatAnalysis {

	/**
	 * A specific strategy for license compatibility tests.
	 */
	private LicenseCompatStrategy compatStrategy;

	/**
	 * Construct the analysis from the internal DOT file.
	 *
	 * @throws ImportException
	 *             thrown when the loading of the internal DOT file failed
	 */
	public LicenseCompatAnalysis() throws ImportException {
		setCompatStrategy(LicenseCompatGraph.createFromResources());
	}

	/**
	 * Construct the analysis from a given compatibility strategy.
	 *
	 * @param compatStrategy
	 *            a specific strategy for license compatibility tests
	 */
	public LicenseCompatAnalysis(final LicenseCompatStrategy compatStrategy) {
		setCompatStrategy(compatStrategy);
	}

	/**
	 * Elements in an SPDX document usually consists of a set of licenses, a
	 * declared license (as expression) and a concluded license (as expression).
	 * This method can be used to analyze such scenarios. The primary input
	 * expression is split to conjunctive sets and for each set a result is
	 * generated.
	 *
	 * @param declaredOrConcludedLicense
	 *            the declared or concluded license expression of an SPDX
	 *            element
	 * @param licensesFromFiles
	 *            the contained licenses of an SPDX element
	 * @return a list of results for each conjunctive input set mapped to a
	 *         declaredOrConcludedLicense subexpression
	 */
	public Map<AnyLicenseInfo, List<LicenseCompatAnalysisResult>> analyse(
			final AnyLicenseInfo declaredOrConcludedLicense, final AnyLicenseInfo[] licensesFromFiles) {

		Utils.ensureNotNull(declaredOrConcludedLicense);
		Utils.ensureNotNull((Object[])licensesFromFiles);

		final Map<AnyLicenseInfo, List<LicenseCompatAnalysisResult>> results = new LinkedHashMap<>();

		for (final AnyLicenseInfo licensingScenario : ExpressionSplitting
				.splitToConjunctiveSets(declaredOrConcludedLicense)) {
			results.put(licensingScenario, analyseExpressions(
					ExpressionMerging.andJoin(licensingScenario, ExpressionMerging.andJoin(licensesFromFiles))));
		}

		return results;
	}

	/**
	 * Tests the compatibility of the given SPDX license expressions. All given
	 * expressions are connected with the AND operator and then split to
	 * conjunctive license sets after simplification. For each of these sets a
	 * result is generated. An example for an input expression is "(GPL-3.0+ AND
	 * LGPL-3.0 AND MIT)".
	 *
	 * @param expressions
	 *            a set of expressions to test the compatibility for
	 * @return a list of results for each conjunctive input set
	 */
	public List<LicenseCompatAnalysisResult> analyseExpressions(final AnyLicenseInfo... expressions) {

		Utils.ensureNotNull((Object[]) expressions);

		final List<LicenseCompatAnalysisResult> results = new ArrayList<>();

		for (final AnyLicenseInfo licensingScenario : ExpressionSplitting
				.splitToConjunctiveSets(ExpressionMerging.andJoin(expressions))) {

			final LicenseCompatAnalysisResult result = new LicenseCompatAnalysisResult();
			result.setLicenses(ExpressionSplitting.splitToLicenses(licensingScenario));

			final List<AnyLicenseInfo> licenses = new ArrayList<>(result.getLicenses());
			AnyLicenseInfo conflicts = new SpdxNoneLicense();

			for (int i = 0; i < licenses.size(); ++i) {
				for (int j = i + 1; j < licenses.size(); ++j) {

					try {
						final AnyLicenseInfo sourceLicense = licenses.get(i);
						final AnyLicenseInfo targetLicense = licenses.get(j);

						if (!getCompatStrategy().areCompatible(sourceLicense, targetLicense)) {
							conflicts = ExpressionMerging.orJoin(conflicts,
									ExpressionMerging.andJoin(sourceLicense, targetLicense));
						}
					} catch (final UnsupportedLicenseException e) {
						result.getUnsupportedLicenses().add(e.getLicense());
					}
				}
			}

			if (TypeInfo.isValid(conflicts)) {
				result.setConflicts(ExpressionSplitting.splitToConjunctiveSets(conflicts));
			}

			results.add(result);
		}

		return results;

	}

	/**
	 * Get the strategy used for license compatibility tests.
	 * 
	 * @return a specific strategy for license compatibility tests
	 */
	public LicenseCompatStrategy getCompatStrategy() {
		return compatStrategy;
	}

	/**
	 * Set the strategy for license compatibility tests.
	 *
	 * @param compatStrategy
	 *            a specific strategy for license compatibility tests
	 */
	public void setCompatStrategy(final LicenseCompatStrategy compatStrategy) {
		this.compatStrategy = compatStrategy;
	}

}