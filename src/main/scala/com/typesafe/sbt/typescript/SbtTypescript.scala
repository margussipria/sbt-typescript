package com.typesafe.sbt.typescript

import java.io.File

import com.typesafe.sbt.jse.JsEngineImport.JsEngineKeys
import com.typesafe.sbt.jse.SbtJsTask
import com.typesafe.sbt.jse.SbtJsTask.autoImport.JsTaskKeys._
import com.typesafe.sbt.web.Import.WebKeys._
import com.typesafe.sbt.web.SbtWeb.autoImport._
import sbt.Keys._
import sbt.{Def, Setting, _}
import spray.json.{JsArray, JsObject, JsString}

object Import {

  object TypescriptKeys {

    val typescript = TaskKey[Seq[File]](
      "typescript",
      "Invoke the typescript compiler."
    )

    val sourceRoot = SettingKey[String](
      "typescript-source-root",
      "Specifies the location where debugger should locate TypeScript files instead of source locations."
    )
    val configFiles = SettingKey[Seq[String]](
      "config-file",
      "Name of the config file. By default the sbt-typescript will look into the assets directory"
    )
  }
}

object SbtTypescript extends AutoPlugin {

  override def requires: SbtJsTask.type = SbtJsTask

  override def trigger: PluginTrigger = AllRequirements

  val autoImport: Import.type = Import

  import Import.TypescriptKeys._

  private val typescriptUnscopedSettings: Seq[Def.Setting[_]] = Seq(

    includeFilter := GlobFilter("*.ts") | GlobFilter("*.tsx"),

    excludeFilter := GlobFilter("*.d.ts"),

    sources := Def.task {
      (sourceDirectory.value ** (includeFilter.value -- excludeFilter.value)).get
    }.value,

    jsOptions := Def.task {
      JsObject(
        "sourceRoot" -> JsString(sourceRoot.value),
        "logLevel" -> JsString(logLevel.value.toString),
        "rootDir" -> JsString(sourceDirectory.value.absolutePath),
        "baseUrl" -> JsString((webJarsDirectory.value / "lib").absolutePath),
        "configFiles" -> JsArray(configFiles.value.map(file => JsString(file)).toVector),
        "projectBase" -> JsString(baseDirectory.value.absolutePath),
        "sources" -> JsArray(
          sources.value
            .filter(_.isFile)
            .map(file => JsString(file.absolutePath))
            .toVector
        )
      ).toString()
    }.value
  )

  def relative(base: String, fullPath: String): String = fullPath.replace(base, "")

  override def buildSettings: Seq[Setting[_]] = {
    SbtJsTask.jsTaskSpecificUnscopedBuildSettings ++ Seq(
      moduleName := "typescript",
      shellFile := getClass.getClassLoader.getResource("typescriptc.js"),
    )
  }

  override def projectSettings: Seq[Def.Setting[_]] = {
    Seq(
      JsEngineKeys.engineType := JsEngineKeys.EngineType.Node,

      sourceRoot := "",
      logLevel := Level.Info,
      Assets / configFiles := Vector(
        relative(baseDirectory.value.absolutePath, ((Assets / sourceDirectory).value / "tsconfig.json").absolutePath)
      ),
      TestAssets / configFiles := Vector.empty,
    ) ++ inTask(typescript)(
      SbtJsTask.jsTaskSpecificUnscopedProjectSettings ++
        Seq(
          Assets / taskMessage := "TypeScript compiling",
          TestAssets / taskMessage := "TypeScript test compiling"
        )
    ) ++ SbtJsTask.addJsSourceFileTasks(typescript) ++ inTask(typescript)(
        inConfig(Assets)(typescriptUnscopedSettings) ++
        inConfig(TestAssets)(typescriptUnscopedSettings)
    ) ++ Seq(
      Assets / typescript := (Assets / typescript).dependsOn(Assets / webModules).value,
      TestAssets / typescript := (TestAssets / typescript).dependsOn(TestAssets / webModules).value,
    )
  }
}
