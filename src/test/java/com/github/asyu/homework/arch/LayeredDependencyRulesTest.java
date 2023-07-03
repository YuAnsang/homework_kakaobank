package com.github.asyu.homework.arch;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.github.asyu.homework", importOptions = ImportOption.DoNotIncludeTests.class)
class LayeredDependencyRulesTest {

  @ArchTest
  static final ArchRule controller_should_not_depend_on_entity =
      noClasses().that()
          .resideInAPackage("com.github.asyu.homework.domain..controller..")
          .should()
          .dependOnClassesThat()
          .resideInAPackage("com.github.asyu.homework.domain..persistence..entity..");

  @ArchTest
  static final ArchRule common_should_not_access_domain =
      noClasses().that()
          .resideInAPackage("com.github.asyu.homework.common..")
          .should()
          .accessClassesThat()
          .resideInAPackage("com.github.asyu.homework.domain..");

  @ArchTest
  static final ArchRule common_should_not_access_infra =
      noClasses().that()
          .resideInAPackage("com.github.asyu.homework.common..")
          .should()
          .accessClassesThat()
          .resideInAPackage("com.github.asyu.homework.infra..");

  @ArchTest
  static final ArchRule infra_should_not_access_common =
      noClasses().that()
          .resideInAPackage("com.github.asyu.homework.infra..")
          .should()
          .accessClassesThat()
          .resideInAPackage("com.github.asyu.homework.common..");
}
