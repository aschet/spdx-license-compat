# spdx-license-compat

Spdx-license-compat is Java library which implements a graph based license compatibility analysis [1] and operates on [SPDX](https://spdx.org/spdx-specification-21-web-version) identifiers and expressions. 

## Operations

License compatibility check:

```java
AnyLicenseInfo GPL = LicenseInfoFactory.parseSPDXLicenseString("GPL-3.0+");
AnyLicenseInfo MIT = LicenseInfoFactory.parseSPDXLicenseString("MIT");

LicenseCompatStrategy compatStrategy = LicenseCompatGraph.createFromResources();
compatStrategy.areCompatible(GPL, MIT); // -> true
```

Compatibility analysis for SPDX expressions:

```java
AnyLicenseInfo expression = LicenseInfoFactory.parseSPDXLicenseString("(((LGPL-3.0+ OR MIT) AND GPL-2.0) OR BSD-3-Clause)");

LicenseCompatAnalysis analysis = new LicenseCompatAnalysis(LicenseCompatGraph.createFromResources());
analysis.analyseExpressions(expression);
```

Compatibility analysis for SPDX elements:

```java
AnyLicenseInfo declaredLicense = LicenseInfoFactory.parseSPDXLicenseString("(AGPL-3.0 OR GPL-3.0)");
AnyLicenseInfo[] licensesFromFiles = new AnyLicenseInfo[3];
licensesFromFiles[0] = LicenseInfoFactory.parseSPDXLicenseString("GPL-3.0");
licensesFromFiles[1] = LicenseInfoFactory.parseSPDXLicenseString("LGPL-3.0+");
licensesFromFiles[2] = LicenseInfoFactory.parseSPDXLicenseString("MIT");

LicenseCompatAnalysis analysis = new LicenseCompatAnalysis(LicenseCompatGraph.createFromResources());
analysis.analyse(declaredLicense, licensesFromFiles);
```

## Limitations

## References

[1] Kapitsaki, Georgia M., Frederik Kramer and Nikolaos D. Tselikas (2016). Automating the License Compatibility Process in Open Source Software with SPDX. In: Journal of Systems and Software. DOI: [10.1016/j.jss.2016.06.064](http://dx.doi.org/10.1016/j.jss.2016.06.064).
