package edu.hm.cs.ob.sweng

/* Dieses Objekt entspricht der sonst üblichen main-Funktion
* in einer Klasse.
* Das Objekt enthält direkt Code der ausgeführt wird. Dazu muss
* es App erweitern.
*/
object MoviestoreApp extends App {
  // um nicht immer FSK.... schreiben zu müssen
  // In Scala können wir an beliebigen Stellen importieren.
  // Wir können auch alles importieren (also eine bestimmte Methode,
  // oder bestimmte Werte, ...)
  // und wir können auch mehrere Dinge importieren
  import FSK.{FSK0,FSK16,FSK18}
  // // das ist die Kurzform für
  // import FSK.FSK0
  // import FSK.FSK16
  // import FSK.FSK18
  // // der Wildcard-Import, der immer vermieden werden sollte (auch in Java!)
  // // geht mit _, also z.B.
  // import FSK._

  // um nicht immer MoviestoreCLI.printAvailableMovies() schreiben zu müssen.
  // Importiert werden nur die Methodennamen, also ohne Klammern bzw. Parameter
  import MoviestoreCLI.printAvailableMovies

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
