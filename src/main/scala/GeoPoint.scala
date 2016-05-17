trait GeoPoint {

  def id: String

  def vector: Vector3d

  def inSight(other: GeoPoint): Boolean =
    Trace.inSight(this.vector, other.vector, OrbitalGraph.EarthRadius)

  def distance(other: GeoPoint): Double =
    Trace.distance(this.vector, other.vector)

  override def hashCode(): Int = id.hashCode() + vector.hashCode()

  override def equals(obj: scala.Any): Boolean = obj match {
    case other: GeoPoint => this.id == other.id && this.vector == other.vector
    case _ => false
  }
}
