package es.mbl_cu.hrse;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

class ArchitectureTest {

    private static final String DOMAIN = "Domain";
    private static final String APPLICATION = "Application";
    private static final String INFRASTRUCTURE = "Infrastructure";

    private final JavaClasses javaClasses = new ClassFileImporter()
        .withImportOption(new ImportOption.DoNotIncludeTests())
        .importPackages(this.getClass().getPackage().getName());

    @Test
    void testDependencies() {
        Architectures.layeredArchitecture().consideringOnlyDependenciesInLayers().withOptionalLayers(true)
            .layer(DOMAIN).definedBy("..domain..")
            .layer(APPLICATION).definedBy("..application..")
            .layer(INFRASTRUCTURE).definedBy("..infrastructure..")

            .whereLayer(DOMAIN).mayOnlyAccessLayers(DOMAIN)
            .whereLayer(APPLICATION).mayOnlyAccessLayers(APPLICATION, DOMAIN)

            .check(javaClasses);
    }

}
