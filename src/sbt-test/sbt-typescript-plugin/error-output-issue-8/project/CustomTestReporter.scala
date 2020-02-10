import sbt._
import xsbti.{Position, Problem, Reporter}

class CustomTestReporter(target: File) extends Reporter {
  /** Reset logging and any accumulated error, warning, message or count. */
  def reset(): Unit = {}

  /** Check whether the logger has received any error since last reset. */
  def hasErrors: Boolean = false

  /** Check whether the logger has received any warning since last reset. */
  def hasWarnings: Boolean = false

  /** Log a summary of the received output since the last reset. */
  def printSummary(): Unit = {}

  /** Return a list of warnings and errors since the last reset. */
  def problems: Array[Problem] = Array.empty

  /** Log a message at a concrete position and with a concrete severity. */
  def log(problem: Problem): Unit = {
    if (
      problem.severity.eq(xsbti.Severity.Error) &&
        problem.message().contains("Type 'number' is not assignable to type 'string'") &&
        problem.position().sourceFile().map[String](_.name).orElse("").contains("bad.ts") &&
        problem.position().line().orElse(0).intValue() == 10
    ) {
      IO.touch(target / "valid-error")
    }
  }

  /** Report a comment. */
  def comment(pos: Position, msg: String): Unit = {}
}
