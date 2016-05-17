object Trace {

  def inSight(source: Vector3d, target: Vector3d, radius: Double): Boolean = {
    !(isViableSegment(source, target, radius) && isIntersecting(source: Vector3d, target: Vector3d, radius: Double))
  }

  private def isViableSegment(source: Vector3d, target: Vector3d, radius: Double): Boolean = {
    val v0 = source
    val v1 = target

    val a = Vector3d.zero - v0
    val b = v1 - v0
    val u = a.dotProduct(b) / b.dotProduct(b)

    0 <= u && u <= 1
  }

  private def isIntersecting(source: Vector3d, target: Vector3d, radius: Double): Boolean = {
    val v0 = source
    val v1 = target
    val dir = v1 - v0

    val a = dir.dotProduct(dir)
    val b = dir.multiply(2).dotProduct(v0)
    val c = v0.dotProduct(v0) - (radius * radius)
    val d = (b * b) - (4 * a * c)

    d >= 0
  }

  def distance(p1: Vector3d, p2: Vector3d): Double = {
    val d = p2 - p1
    Math.sqrt(Math.pow(d.x, 2) + Math.pow(d.y, 2) + Math.pow(d.z, 2))
  }
}
