package com.github.asyu.homework.arch;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.github.asyu.homework", importOptions = ImportOption.DoNotIncludeTests.class)
class LayeredArchitectureTest {

  @ArchTest
  static final ArchRule layer_dependencies_are_respected = layeredArchitecture().consideringAllDependencies()
      .layer("Controller").definedBy("com.github.asyu.homework.domain..controller..")
      .layer("Service").definedBy("com.github.asyu.homework.domain..service..")
      .layer("Implement").definedBy("com.github.asyu.homework.domain..implement..")
//      .layer("Dao").definedBy("com.github.asyu.homework.domain..persistence..dao..")
//      .layer("Repository").definedBy("com.github.asyu.homework.domain.persistence..repository..")
      .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
      .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
      .whereLayer("Implement").mayOnlyBeAccessedByLayers("Service")
//      .whereLayer("Dao").mayOnlyBeAccessedByLayers("Service")
//      .whereLayer("Repository").mayOnlyBeAccessedByLayers("Dao")
      ;

}
