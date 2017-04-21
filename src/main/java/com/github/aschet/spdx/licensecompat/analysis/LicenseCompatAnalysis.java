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
 * 
 * @author Thomas Ascher
 *
 */
public class LicenseCompatAnalysis {

	/**
	 * 
	 */
	private LicenseCompatStrategy compatStrategy;

	/**
	 * 
	 * @throws ImportException
	 */
	public LicenseCompatAnalysis() throws ImportException {
		setCompatStrategy(LicenseCompatGraph.createFromResources());
	}

	/**
	 * 
	 * @param compatProvider
	 */
	public LicenseCompatAnalysis(final LicenseCompatStrategy compatProvider) {
		setCompatStrategy(compatProvider);
	}

	/**
	 * 
	 * @param declaredOrConcludedLicense
	 * @param licensesFromFiles
	 * @return
	 */
	public Map<AnyLicenseInfo, List<LicenseCompatAnalysisResult>> analyse(
			final AnyLicenseInfo declaredOrConcludedLicense, final AnyLicenseInfo[] licensesFromFiles) {

		Utils.ensureNotNull(declaredOrConcludedLicense);
		Utils.ensureNotNull((Object[]) licensesFromFiles);

		final Map<AnyLicenseInfo, List<LicenseCompatAnalysisResult>> results = new LinkedHashMap<>();

		for (final AnyLicenseInfo licensingScenario : ExpressionSplitting
				.splitToConjunctiveSets(declaredOrConcludedLicense)) {
			results.put(licensingScenario, analyseExpressions(
					ExpressionMerging.andJoin(licensingScenario, ExpressionMerging.andJoin(licensesFromFiles))));
		}

		return results;
	}

	/**
	 * 
	 * @param expressions
	 * @return
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
	 * 
	 * @return
	 */
	public LicenseCompatStrategy getCompatStrategy() {
		return compatStrategy;
	}

	/**
	 * 
	 * @param compatStrategy
	 */
	public void setCompatStrategy(final LicenseCompatStrategy compatStrategy) {
		this.compatStrategy = compatStrategy;
	}

}