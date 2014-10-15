package edu.hm.cs.ob.sweng

/* Dieses Objekt entspricht der sonst üblichen main-Funktion
 * in einer Klasse.
 * Das Objekt enthält direkt Code der ausgeführt wird. Dazu muss
 * es App erweitern.
 */
object MoviestoreApp extends App {
  // um nicht immer FSK.... schreiben zu müssen
  import FSK._
  // um nicht immer MoviestoreCLI.... schreiben zu müssen
  import MoviestoreCLI._
  // irgendein Beispielcode zum Ausprobieren
  Moviestore.add(new Movie("Into the Mind", FSK0))
  Moviestore.add(new Movie("Texas Chainsaw Massacre", FSK18))
  Moviestore.add(new Movie("Inglourious Basterds", FSK16))
  val ich = Moviestore.add(new Client("Oliver Braun", 42))
  printAvailableMovies()
  println(Moviestore.rent(ich, 1))
  printAvailableMovies()
  Moviestore.handIn(ich, 1)
  printAvailableMovies()
  print(Moviestore)
}