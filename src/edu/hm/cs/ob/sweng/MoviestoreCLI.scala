package edu.hm.cs.ob.sweng

/* Hier sind ein paar wenige Funktionen für eine
 * Kommandozeileninterface (Command Line Interface)
 * angegeben. Sie können gerne weitere schreiben.
 */
object MoviestoreCLI {

  def printAvailableMovies(): Boolean = {
    val avail = Moviestore.availableMovies
    if (avail.isEmpty) {
      println("Sorry, alle Filme ausgeliehen.\nBitte kommen Sie morgen wieder")
      return false
    }
    println("Verfuegbare Filme")
    println("================")

    for ((serial, (movie, _)) <- avail) {
      printf("%3d. %s\n", serial, movie)
    }
    return true
  }

  def rentMovie() {
    if (printAvailableMovies()) {
      print("Nummer des Filmes den Sie ausleihen wollen? ")
      val serial = readInt

    }
  }
}