package edu.hm.cs.ob.sweng

/* Ein Standalone-Objekt gibt es in Java nicht. Um das selbe zu erreichen,
* müssten wir einfach alles was in dem object steht mit static definieren.
*/
object Moviestore {
  // Eine Seriennummer ist ein Int. Serial als Typ zu schreiben, ist aber besser lesbar.
  // Mit type definieren wir ein Typ Synonym.
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
  * werden. Eine Konvention besagt, dass eine Funktion die Nebeneffekte (z.B.
  * I/O) hat, mit leerem Klammerpaar geschrieben werden soll.
  * Der Rückgabetyp steht nicht, wie in Java, vor dem Funktionsnamen,
  * sondern mit einem ':' dahinter. Nachdem es in Scala keine primitiven Datentypen
  * gibt, werden diese Typen auch groß geschrieben (Int statt int).
  * Immer wenn eine Funktion etwas zurück gibt, also keine Prozedur ist,
  * muss zwischen der Signatur und dem Rumpf ein Gleichheitszeichen stehen.
  */
  private def nextMSerial: Serial = {
    mSerial += 1
    // Der Wert des letzten Ausdrucks wird zurück gegeben, es ist kein return
    // notwendig. Übrigens hat eine Zuwweisung in Scala, i. Ggs zu Java, den Typ Unit.
    mSerial
  }

  // TODO: hier fehlt der Code für die Seriennummer der Kunden

  // Für die Kunden soll es ebenfalls eine Map mit Kundennummer geben
  private var clients = Map[Serial, Client]()

  /* Um einen Film zum Bestand hinzuzufügen.
  * Diese Methode gibt nichts zurück. Das heisst in Scala Unit und
  * nicht void. Es kann aber weggelassen werden und das Gleichheitszeichen
  * steht in diesem Fall auch nicht d, also
  * def add(movie: Movie) { ...
  * Guter Stil ist es aber, auch bei Prozeduren ein Gleichheitszeichen und den
  * Rückgabetyp anzugeben.
  */
  def add(movie: Movie): Unit = {
    /* Die Map contents ist unveränderlich. Das ist oftmals der Standard
    * in Scala. Konsequenz ist, dass wir der Map nichts hinzufügen können.
    * Wir können aus der Map und einem neuen Schlüssel-Wert-Paar mit + eine
    * neue Map erzeugen und, nachdem die Variable contents ja veränderbar ist,
    * der Variable contents neu zuweisen. Idee klar?
    * Das Schlüsselwertpaar können wir als Paar schreiben:
    * (nextSerial, (movie, None)) oder mit dem Pfeil, so dass der Schlüssel
    * auf den Wert zeigt. Dies ist die üblichere Schreibweise.
    */
    contents += nextMSerial ->(movie, None)
  }

  // TODO: Hier fehlt der Code um einen neuen Kunden in die Kundenmap einzufügen
  // Im Gegensatz zur Methode add für den Movie soll diese add-Methode die
  // Seriennummer des neuen Kunden als Ergebnis zurück liefern
  def add(client: Client): Serial = {
    // das return 0 kann dann natürlich nicht stehen bleiben
    0
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
    clients get clientSerial match {
      /* case ist wie in Java und dahinter wird das Muster geschrieben.
      * Das Some mit irgendwas, ist dabei das Muster. Das irgendwas heisst
      * hier client und ist dann die Variable in der der gefundene Kunde steht.
      * Statt dem : in Java steht hier ein =>
      * und wir brauchen kein break
      */
      case Some(client) =>
        // wenn wir einen Kunden haben suchen wir nach dem Film,
        // wieder mit get und wieder mit Pattern Matching
        contents get movieSerial match {
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
          case Some((movie, None)) if movie ageOKFor client.age =>
            // Also fügen wir einfach ein neues Schlüsselwertpaar mit
            // dem gefundenen Film ein. Nachdem der Schlüssel (movieSerial)
            // gleich bleibt wird der alte Wert einfach überschrieben.
            contents += movieSerial ->(movie, Some(clientSerial))
            // und dann geben wir noch den Film als Option (Some...) zurück
            Some(movie)
          /* In allen anderen Fällen
          * - Film nicht gefunden
          * - Film gefunden aber ausgeliehen
          * - Film gefunden nicht ausgeliehen aber Kunde zu jung
          * wird None zurück gegeben. case _ spielt die Rolle von default
          * in switch-case. _ ist das Wildcard-Pattern auf das alles passt.
          */
          case _ => None
        }
      // das ist der zweite Fall bzgl der Kundenermittlung
      case _ => None
      // Wie zu sehen ist, ist hier auch nirgends ein return notwendig.
    }
  }

  /* Nachdem rent im None-Fall auch immer None zurück gibt, ist es möglich
   * viel eleganter zu schreiben:
   */
  def rentElegantBody(clientSerial: Serial, movieSerial: Serial): Option[Movie] = {
    // Das ist eine for comprehension. Damit ist es möglich Werte aufzusammeln, für die
    // eine oder mehrere Bedingungen wahr sind. Die Zeilen mit <- heißen Generatoren.
    // Wenn in einer der Zeilen nichts generiert wird, d.h. None herauskam, ist das Ergebnis
    // insgesamt None. Wenn in beiden Fällen Some(...) herauskam. wird das Some durch den
    // <- jeweils entfernt und wir haben die Werte ohne den Option-Typ.
    // Wenn dann noch die Bedingung hinter dem if wahr ist, wird der movie genommen und
    // durch das yield "aufgesammelt", d.h. in dem Fall wieder in ein Some verpackt.
    // Randbemerkung: Das Ganze ist übrigens das Konzept einer sog. Monade.
    val maybeMovie = for {
      client <- clients get clientSerial
      (movie, None) <- contents get movieSerial
      if movie ageOKFor client.age
    } yield movie
    //  // Das geht auch mit anderen Containern, z.B. mit Listen:
    //  for {
    //    // laufe mit x von 1 bis 3
    //    x <- List(1, 2, 3)
    //    // laufe mit y von 5 bis 6
    //    y <- List(5, 6)
    //    // wenn y nicht durch x teilbar ist
    //    if y % x != 0
    //  } yield x + y // sammel die Summen auf
    // map wendet eine Funktion auf den Inhalt von Some an
    // Im folgenden Fall wird der Inhalt (movie) aber nicht verändert.
    // Wenn maybeMovie None war, passiert nichts.
    // movie => ... ist übrigens eine anonyme Funktion/Lambdaausdruck/Closure.
    maybeMovie map { movie =>
      contents += movieSerial ->(movie, Some(clientSerial))
    }
    // // Map funktioniert auch für andere Container, z.B. Listen:
    // List(1,2,3,4) map { x => x+1 }
    // // oder kürzer mit einem Platzhalter für das fehlende Argument:
    // List(1,2,3,4) map { _+1 }
    maybeMovie
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
    false
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
    // Genau genommen ist es wieder eine for comprehension :-)
    // Daher verwenden wir auch geschweifte statt runder Klammern
    for {(key, (_, maybeClient)) <- contents}
      if (maybeClient nonEmpty) {
        newMap -= key
      }
    newMap
  }

  // rentedMovies soll die Map mit allen ausgeliehenen berechnen.
  def rentedMovies: Map[Serial, (Movie, Option[Serial])] = {
    // TODO: Code fehlt. return contents macht natürlich gar keinen Sinn
    contents
  }

  // ganz simple Redefinition von toString
  // override ist in Scala ein Schlüsselwort und wird vom Compiler gecheckt
  // Nachdem die Methode aus nur einem Ausdruck besteht, können die geschweiften
  // Klammern weg gelassen werden. Dies ist in diesem Fall der Lesbarkeit sogar
  // dienlich.
  override def toString: String =
    "Gesamter Inhalt\n" ++ contents.toString() ++
      "\nKundenstamm\n" ++ clients.toString() ++
      "\nVerfügbare Filme\n" ++ availableMovies.toString() ++
      "\nAusgeliehene Filme\n" ++ rentedMovies.toString() ++ "\n"

}
