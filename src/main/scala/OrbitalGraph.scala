object OrbitalGraph {

  val DefaultUrl: String = "https://space-fast-track.herokuapp.com/generate"
  val EarthRadius: Double = 6371

  def main(args: Array[String]) {
    val t1 = System.currentTimeMillis()
    val data = OrbitData.load(args.headOption.getOrElse(DefaultUrl))
    val t2 = System.currentTimeMillis()

    println(s"Finding signal path for seed ${data.seed}")

    val t3 = System.currentTimeMillis()
    val path = data.path()
    val t4 = System.currentTimeMillis()

    println(path.map(nodes => nodes.map(_.id).mkString(", ")).getOrElse("no path found"))

    println()
    println(s": Loaded data in ${t2 - t1}ms")
    println(s": Built graph and solved shortest path in ${t4 - t3}ms")
  }
}
