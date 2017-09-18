import microsites._

enablePlugins(MicrositesPlugin)
scalaVersion := "2.11.8"

micrositeGitHostingService := GitLab
micrositeGitHostingUrl := "https://gitlab.com/gitlab-org/gitlab-ce"

micrositeExtraMdFiles := Map(
  file("README.md") -> ExtraMdFileConfig(
    "index.md",
    "home"
  )
)

def getLines(fileName: String) =
  IO.readLines(file(fileName))

lazy val check = TaskKey[Unit]("check")

check := {
  val content = getLines("target/site/index.html").mkString

  if (!content.contains("View on GitLab"))
    sys.error("micrositeGitHostingService not configured properly")
  if (!content.contains("https://gitlab.com/gitlab-org/gitlab-ce"))
    sys.error("micrositeGitHostingUrl not configured properly")
  if (!content.contains("fa-gitlab"))
    sys.error("hosting FA icon not chosen properly")
}
