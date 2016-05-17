object Vector3d {

  val zero = Vector3d(0, 0, 0)

  def apply(coordinate: Coordinate, radius: Double): Vector3d = Vector3d(
    x = radius * Math.cos(radians(coordinate.latitude)) * Math.cos(radians(coordinate.longitude)),
    y = radius * Math.cos(radians(coordinate.latitude)) * Math.sin(radians(coordinate.longitude)),
    z = radius * Math.sin(radians(coordinate.latitude))
  )

  private def radians(degrees: Double): Double = degrees * Math.PI / 180
}

case class Vector3d(x: Double, y: Double, z: Double) {

  def -(other: Vector3d): Vector3d = subtract(other)

  def subtract(other: Vector3d): Vector3d = {
    Vector3d(x - other.x, y - other.y, z - other.z)
  }

  def times(other: Vector3d): Vector3d = {
    Vector3d(x * other.x, y * other.y, z * other.z)
  }

  def multiply[T](scalar: T)(implicit n: Numeric[T]): Vector3d = {
    val s = n.toDouble(scalar)
    Vector3d(x * s, y * s, z * s)
  }

  def dotProduct(other: Vector3d): Double = {
    (x * other.x) + (y * other.y) + (z * other.z)
  }
}
