import scala.io.{Codec, Source}
import scalax.collection.Graph
import scalax.collection.edge.Implicits._
import scalax.collection.edge.WLUnDiEdge

object OrbitData {

  def load(url: String): OrbitData = {
    val iterator = Source.fromURL(url, Codec.UTF8.name).getLines()
    val seed: String = iterator.next().substring(7)
    var satellites: List[Satellite] = List.empty
    var route: Route = null
    while (iterator.hasNext) {
      iterator.next().split(',') match {
        case Array(id, lat, lon, alt) if id.startsWith("SAT") =>
          satellites = satellites :+ Satellite(id, Coordinate(lat.toDouble, lon.toDouble), alt.toDouble)

        case Array(r, startLat, startLon, endLat, endLon) if r == "ROUTE" =>
          route = Route(Coordinate(startLat.toDouble, startLon.toDouble), Coordinate(endLat.toDouble, endLon.toDouble))
      }
    }
    OrbitData(seed, route, satellites)
  }
}

case class OrbitData(seed: String, route: Route, satellites: List[Satellite]) {

  def buildGraph(): Graph[GeoPoint, WLUnDiEdge] = {
    def edge(g1: GeoPoint, g2: GeoPoint) = (g1 ~%+ g2)(g1.distance(g2).toLong, s"${g1.id} - ${g2.id}")

    val start = route.startPoint
    val end = route.endPoint

    satellites.foldLeft(Graph[GeoPoint, WLUnDiEdge]())((graph, satellite) => {
      var g = graph
      if (start.inSight(satellite)) {
        g = g + edge(start, satellite)
      }
      if (end.inSight(satellite)) {
        g = g + edge(satellite, end)
      }
      g ++ satellites.filter(_.inSight(satellite)).map(s => edge(satellite, s))
    })
  }

  def path(): Option[List[GeoPoint]] = {
    val g = buildGraph()
    def n(outer: GeoPoint): g.NodeT = g get outer

    val path = n(route.startPoint) shortestPathTo n(route.endPoint)
    path.map(p => p.nodes.map(_.value).toList)
  }

}
