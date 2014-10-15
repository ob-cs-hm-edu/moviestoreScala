package edu.hm.cs.ob.sweng

/* Ein Standalone-Objekt gibt es in Java nicht. Um das selbe zu erreichen,
 * müssten wir einfach alles was in dem object steht mit static definieren.
 */
object Moviestore {
  // Eine Seriennummer ist ein Int.
  type Serial = Int
  /* Der Inhalt einer Videothek ist ein einer Map gespeichert.
   * Schlüssel sind die Seriennummern (Ints).
   * Werte sind Paare (Tupel) bestehend aus einem Film
   * und optional einem Kunden, der den Film ausgeliehen hat.
   * Hat kein Kunde den Film ausgeliehen, ist der Wert None.
   * Hat der Kunde hubert den Film ausgeliehen, ist der Wert
   * Some(hubert). Dadurch ersparen wir und Null-Pointer.
   * Option gibt es in Java 8 auch.
   * Eine var ist eine veränderliche Variable. Einen Typ müssen wir
   * nicht angeben. Wäre Sie unveränderlich (was grundsätzlich vorzuziehen
   * ist) schreiben wir val.
   * Die Typen die in einer Map sind, werden nicht wie in Java in <>
   * geschrieben, sondern in [].
   */
  private var contents = Map[Serial, (Movie, Option[Serial])]()
  // Um die Seriennummer zu generieren.
  private var mSerial = 0
  /* Mit def beginnt eine Funktionsdefinition. Wenn die Funktion
   * keine Argumente hat, kann das leere Klammerpaar auch weg gelassen
   * werden. Der Rückgabetyp steht nicht, wie in Java, vor dem Funktionsnamen,
   * sondern mit einem ':' dahinter. Nachdem es in Scala keine primitiven Datentypen
   * gibt, werden ganze Zahlen auch groß geschrieben (Int statt int).
   * Immer wenn eine Funktion etwas zurück gibt, also keine Prozedur ist,
   * muss zwischen der Signatur und dem Rumpf ein Gleichheitszeichen stehen.
   */
  private def nextMSerial: Serial = {
    mSerial += 1
    return mSerial
  }
  // TODO: hier fehlt der Code für die Seriennummer der Kunden

  // Für die Kunden soll es ebenfalls eine Map mit Kundennummer geben
  private var clients = Map[Serial, Client]()
  /* Um einen Film zum Bestand hinzuzufügen.
   * Diese Methode gibt nichts zurück. Das heisst in Scala Unit und
   * nicht void. Es kann aber weggelassen werden und das Gleichheitszeichen
   * steht in diesem Fall auch nicht d.
   */
  def add(movie: Movie) {
    /* Die Map contents ist unveränderlich. Das ist oftmals der Standard
	 * in Scala. Konsequenz ist, dass wir der Map nichts hinzufügen können.
	 * Wir können aus der Map und einem neuen Schlüssel-Wert-Paar mit + eine
	 * neue Map erzeugen und, nachdem die Variable contents ja veränderbar ist,
	 * der Variable contents neu zuweisen. Idee klar?
	 * Das Schlüsselwertpaar können wir als Paar schreiben:
	 * (nextSerial, (movie, None)) oder mit dem Pfeil, so dass der Schlüssel
	 * auf den Wert zeigt. Dies ist die üblichere Schreibweise.
	 */
    contents += nextMSerial -> (movie, None)
  }
  // TODO: Hier fehlt der Code um einen neuen Kunden in die Kundenmap einzufügen
  // Im Gegensatz zur Methode add für den Movie soll diese add-Methode die
  // Seriennummer des neuen Kunden als Ergebnis zurück liefern
  def add(client: Client): Serial = {
    // das return 0 kann dann natürlich nicht stehen bleiben
    return 0
  }
  /* Die Methode rent bekommt die Seriennummern des Kunden und des Filmes
   * den er ausleihen will. Wenn das Ausleihen erfolgreich ist, wird der
   * Film in einem Some vepackt zurück gegeben, sonst ein None.
   */
  def rent(clientSerial: Serial, movieSerial: Serial): Option[Movie] = {
    /* Zuerst wird versucht den Kunden mit Hilfe der Seriennummer zu ermitteln.
     * Das geht mit clients.get(clientSerial). In Scala kann man bei Methoden
     * mit einem Parameter den Punkt und die Klammern weglassen um es mehr
     * wie einen Operator aussehen zu lassen.
     * Beim Zugriff mit get kommt ein Wert vom Typ Option[Client] heraus.
     * Um je nach Ergebnis weiter arbeiten zu können, bietet sich sog.
     * Pattern Matching an. Das ist so ähnlich wie switch und case in Java,
     * aber viel mächtiger.
     * Statt switch schreibt man match.
     */
    (clients get clientSerial) match {
      /* case ist wie in Java und dahinter wird das Muster geschrieben.
       * Das Some mit irgendwas, ist dabei das Muster. Das irgendwas heisst
       * hier client und ist dann die Variable in der der gefundene Kunde steht.
       * Statt dem : in Java steht hier ein =>
       * und wir brauchen kein break
       */
      case Some(client) =>
        // wenn wir einen Kunden haben suchen wir nach dem Film,
        // wieder mit get und wieder mit Pattern Matching
        (contents get movieSerial) match {
          /* im case geben wir dieses Mal wieder ein Some-Muster an.
           * dieses soll ein Tupel enthalten, in dem in der ersten
           * Komponente ein beliebiger Movie und in der zweiten
           * Komponente ein None, für nicht ausgeliehen, steht.
           * damit wissen wir über das Pattern Matching schon, dass der
           * Film nicht ausgeliehen ist.
           * Was uns gleich auch noch interessiert ist, ob der Kunde
           * überhaupt alt genug für den Film ist. Und das können wir
           * gleich nach dem Pattern und vor dem => mit einer Bedingung
           * abprüfen. Mit Punkt und Klammern hätten wir wieder
           * movie.ageOK(client.age) schreiben können.
           * Zusammenfassend heisst das also, dass er in diesen Fall nur dann
           * hinein geht, wenn
           * 1. Film mit Serial gefunden (Some(...))
           * 2. Film nicht ausgeliehen (None)
           * 3. Kunde alt genug (if...)
           */
          case Some((movie, None)) if movie ageOK client.age =>
            // Also fügen wir einfach ein neues Schlüsselwertpaar mit
            // dem gefundenen Film ein. Nachdem der Schlüssel (movieSerial)
            // gleich bleibt wird der alte Wert einfach überschrieben.
            contents += movieSerial -> (movie, Some(clientSerial))
            // und dann geben wir noch den Film als Option (Some...) zurück
            return Some(movie)
          /* In allen anderen Fällen
           * - Film nicht gefunden
           * - Film gefunden aber ausgeliehen
           * - Film gefunden nicht ausgeliehen aber Kunde zu jung
           * wird None zurück gegeben. case _ spielt die Rolle von default
           * in switch-case. _ ist das Wildcard-Pattern auf das alles passt.
           */ 
          case _ => return None
        }
      // das ist der zweite Fall bzgl der Kundenermittlung
      case _ => return None
    }
  }
  /* Die Methode zum zurück geben eines Films fehlt wieder.
   * Es soll true zurück gegeben werden, wenn die Rückgabe erfolgreich war
   * und false sonst.
   * Die Rückgabe funktioniert (und soll dann durchgeführt werden), wenn
   * der Kunde existiert, der Film gefunden wird un dvon diesem Kunden
   * tatsächlich ausgeliehen wurde (Nutzen Sie ==, denn es soll wirklich
   * das selbe Objekt sein). In allen anderen Fällen wird die Rücknahme nicht
   * akzeptiert.
   */
  def handIn(clientSerial: Serial, movieSerial: Int): Boolean = {
    // TODO: Code einfügen
    return false
  }
  /* Die Methode availableMovies berechnet aus der Map aller
   * Filme, die Map, die nur verfügbare Filme enthält, in dem sie
   * alle ausgeliehenen Filme löscht. Nachdem wir mit unveränderbaren
   * Maps arbeiten, müssen wir nicht aufpassen, dass wir nichts aus dem
   * contents löschen. Das geht nämlich gar nicht.
   */
  def availableMovies: Map[Serial, (Movie, Option[Serial])] = {
    var newMap = contents
    // das ist eine foreach-Schleife. Vorne kann ein Pattern stehen und
    // statt dem : wird der <- geschrieben
    for ((key, (_, maybeClient)) <- contents)
      maybeClient match {
        case Some(_) => newMap -= key
        case _ =>
      }
    return newMap
  }
  // rentedMovies soll die Map mit allen ausgeliehenen berechnen.
  def rentedMovies: Map[Serial, (Movie, Option[Serial])] = {
    // TODO: Code fehlt. return contents macht natürlich gar keinen Sinn
    return contents
  }
  // ganz simple Redefinition von toString
  // override ist in Scala ein Schlüsselwort
  override def toString(): String = {
    return "Gesamter Inhalt\n" ++ contents.toString() ++ "\n" ++
    		"Kundenstamm\n" ++ clients.toString() ++ "\n" ++
    		"Verfügbare Filme\n" ++ availableMovies.toString() ++ "\n" ++
    		"Ausgeliehene Filme\n" ++ rentedMovies.toString() ++ "\n"
  
  }
}
