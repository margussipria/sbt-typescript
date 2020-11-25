
name := "sbt-typescript"

homepage := Some(new URL("https://github.com/margussipria/sbt-typescript"))
licenses := Seq(("Apache 2", new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")))
organization := "eu.sipria.sbt"

scalaVersion := "2.12.12"

developers := List(
  // Developer("barp", "Brandon Arp", "brandon@arpnetworking.com", url("http://www.arpnetworking.com")),
  Developer("margussipria", "Margus Sipria", "margus+sbt-cxf@sipria.fi", url("https://github.com/margussipria"))
)

scmInfo := Some(ScmInfo(
  browseUrl = url("http://github.com/margussipria/sbt-typescript"),
  connection = "scm:git:https://github.com/margussipria/sbt-typescript.git",
  devConnection = Some("scm:git:git@github.com:margussipria/sbt-typescript.git")
))

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .enablePlugins(SbtWeb)
  .settings(
    addSbtPlugin("com.typesafe.sbt" % "sbt-js-engine" % "1.2.3"),

    libraryDependencies ++= Seq(
      "org.webjars.npm" % "typescript" % "3.9.7",
      "com.typesafe" % "jstranspiler" % "1.0.1"
    )
  )

resolvers ++= Seq(
  Resolver.mavenLocal,
  Resolver.typesafeRepo("releases")
)

scalacOptions += "-feature"

publishMavenStyle := true

// TODO: like why is there META-INF/resources/webjars/sbt-typescript/0.5.1-SNAPSHOT/ containing all the assets after this?
scriptedDependencies := scriptedDependencies.dependsOn(TypescriptKeys.typescript in Assets).value
publish := publish.dependsOn(TypescriptKeys.typescript in Assets).value
publishLocal := publishLocal.dependsOn(TypescriptKeys.typescript in Assets).value
//compile in Compile := (compile in Compile).dependsOn(TypescriptKeys.typescript in Assets).value
resourceDirectory in Compile := baseDirectory.value / "target" / "web" / "typescript" / "main" // // :(
