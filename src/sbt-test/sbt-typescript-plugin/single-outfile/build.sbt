import java.nio.file.Files

import com.typesafe.sbt.typescript.Import.TypescriptKeys._
import sbt.complete.DefaultParsers._

import scala.annotation.tailrec

lazy val root = (project in file("."))
  .enablePlugins(SbtWeb)
  .settings(
    JsEngineKeys.engineType := JsEngineKeys.EngineType.Node,
    sourceRoot  := "/assets/"
  )

includeFilter in (Assets, typescript) := GlobFilter("*.ts") | GlobFilter("*.tsx") | GlobFilter("*.js") | GlobFilter("*.jsx")

lazy val assertMatches = inputKey[Unit]("")

assertMatches := {
  spaceDelimited("<arg>").parsed.map(file) match {
    case Seq(actual, expected) =>
      assert(actual.exists, s"Generated file ${actual.getName} does not exist")
      assert(expected.exists, s"Expected file ${expected.getName} does not exist")

      val actualFile = readAllText(actual).trim
      val expectedFile = readAllText(expected).trim

      @tailrec
      def compareLines(expected: List[String], actual: List[String], lineNumber: Int = 1): Unit = {
        (expected, actual) match {
          case (Nil, Nil) =>
          case (expectedLine :: expectedMore, actualLine :: actualMore) =>
            if (expectedLine != actualLine) {
              throw new Error(
                s"Generated file line $lineNumber does not match expected\n" +
                  s"expected: '$expectedLine'\n" +
                  s"  actual: '$actualLine'"
              )
            }

            compareLines(expectedMore, actualMore, lineNumber + 1)
          case (Nil, line :: _) =>
            throw new Error(s"Generated file contained unexpected line $lineNumber with content:\n'$line'")
          case (line :: _, Nil) =>
            throw new Error(s"Actual file contained more content that was generated. Expected line $lineNumber with content:\n'$line'")
        }
      }

      compareLines(expectedFile.linesIterator.toList, actualFile.linesIterator.toList)

      assert(actualFile == expectedFile, s"Generated file ${actual.getName} does not match expected")
  }
}

def readAllText(file: File) = new String(Files.readAllBytes(file.toPath), "UTF-8")

