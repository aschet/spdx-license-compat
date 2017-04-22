# spdx-license-compat

Spdx-license-compat is a Java library which implements a graph based license compatibility analysis [1] and operates on [SPDX](https://spdx.org/spdx-specification-21-web-version) identifiers and expressions. While some design decisions and the graph database were derived from the [SPDX License Compatibility RESTful Service](https://github.com/dpasch01/spdx-compat-tools), this the library itself is a clean-room implementation. Spdx-license-compat builds on top of the [SPDX tools](https://github.com/spdx/tools) and provides the following features:
- Comatibility check between SPDX license identifiers (with `+` and `WITH` operator) and non SPDX listed licenses.
- Compatibility analysis of SPDX license expressions.
- Compatibility analysis for SPDX elements with dual or multi licensing.

## Operations

License compatibility check:

```java
AnyLicenseInfo GPL = LicenseInfoFactory.parseSPDXLicenseString("GPL-3.0+");
AnyLicenseInfo MIT = LicenseInfoFactory.parseSPDXLicenseString("MIT");

LicenseCompatStrategy compatStrategy = LicenseCompatGraph.createFromResources();
compatStrategy.areCompatible(GPL, MIT); // -> true
```

Compatibility analysis for SPDX license expressions:

```java
AnyLicenseInfo expression = LicenseInfoFactory.parseSPDXLicenseString("(((LGPL-3.0+ OR MIT) AND GPL-2.0) OR BSD-3-Clause)");

LicenseCompatAnalysis analysis = new LicenseCompatAnalysis(LicenseCompatGraph.createFromResources());
analysis.analyseExpressions(expression); // conflict between LGPL-3.0+ and GPL-2.0
```

Compatibility analysis for SPDX elements:

```java
AnyLicenseInfo declaredLicense = LicenseInfoFactory.parseSPDXLicenseString("(AGPL-3.0 OR GPL-3.0)");
AnyLicenseInfo[] licensesFromFiles = new AnyLicenseInfo[3];
licensesFromFiles[0] = LicenseInfoFactory.parseSPDXLicenseString("GPL-3.0");
licensesFromFiles[1] = LicenseInfoFactory.parseSPDXLicenseString("LGPL-3.0+");
licensesFromFiles[2] = LicenseInfoFactory.parseSPDXLicenseString("MIT");

LicenseCompatAnalysis analysis = new LicenseCompatAnalysis(LicenseCompatGraph.createFromResources());
analysis.analyse(declaredLicense, licensesFromFiles); // no conflicts
```

## Limitations

- License exceptions are irgnore by the compatibility analysis.
- Forward compatibility checking is currently not implemented (e.g. `GPL-2.0+ -> GPL-3.0` vs `GPL-3.0 -> GPL-2.0+`).
- No possible adjustments are suggested in case of license conflicts. This feature is present in the [SPDX License Compatibility RESTful Service](https://github.com/dpasch01/spdx-compat-tools).
- Only a fraction of the existing SPDX license identifiers is supported: AFL-3.0, AGPL-1.0, AGPL-3.0, Apache-1.0, Apache-2.0, APSL-1.0, Artistic-2.0, BSD-2-Clause-FreeBSD, BSD-3-Clause, CDDL-1.0, CDDL-1.1, CECILL-2.0, EUPL-1.1, GPL-2.0, GPL-2.0, GPL-3.0, LGPL-2.1, LGPL-3.0, Libpng, MIT, MPL-1.1, MPL-2.0, OSL-3.0, OSL-3.0, X11, Zlib.

## Compiling and Integration

Maven is used as build system. To build from source use:

```
mvn package
```

spdx-license-compat is available via the Maven Central Repository:

```
<dependency>
  <groupId>com.github.aschet</groupId>
  <artifactId>spdx-license-compat</artifactId>
  <version>1.0.0</version>
</dependency>
```

## References

[1] Kapitsaki, Georgia M., Frederik Kramer and Nikolaos D. Tselikas (2016). Automating the License Compatibility Process in Open Source Software with SPDX. In: Journal of Systems and Software. DOI: [10.1016/j.jss.2016.06.064](http://dx.doi.org/10.1016/j.jss.2016.06.064).
