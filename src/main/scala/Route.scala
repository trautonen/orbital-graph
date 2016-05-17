case class Route(start: Coordinate, end: Coordinate) {

  def startPoint: GeoPoint = new GeoPoint {
    override def id: String = "START"
    override def vector: Vector3d = Vector3d(start, OrbitalGraph.EarthRadius)
  }

  def endPoint: GeoPoint = new GeoPoint {
    override def id: String = "END"
    override def vector: Vector3d = Vector3d(end, OrbitalGraph.EarthRadius)
  }
}
