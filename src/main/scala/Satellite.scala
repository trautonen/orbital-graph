case class Satellite(id: String, coordinate: Coordinate, altitude: Double) extends GeoPoint {

  override def vector: Vector3d = Vector3d(coordinate, OrbitalGraph.EarthRadius + altitude)

}
