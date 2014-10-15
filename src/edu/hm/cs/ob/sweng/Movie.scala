package edu.hm.cs.ob.sweng
/*in Scala kann man (fast) alles an (fast) beliebigen Stellen importieren
 * Damit wir nicht immer FSK.FSK, FSK.FSK0, ... schreiben müssen importieren
 * wir hier mal alles aus FSK.
 */
import FSK._

/* Eine Klasse für Filme. Statt einen einfachen Konstruktor zu schreiben, der
 * title und fskVal nimmt und an zwei Objektvariablen zuweist, können wir 
 * den Titel und die FSK-Freigabe einfach als sog. Klassenparameter angeben.
 * Daraus generiert der Scala-Compiler dann quasi Objektvariablen (die
 * standardmäßig private und unveränderlich sind) und den passenden Konstruktor.
 */
class Movie(title: String, fskVal: FSK) {
  /* Die Methode fsk ist ein Getter für den FSK-Wert. Nachdem wir die
   * Methode ohne leere Klammern schreiben, füllt sich die Nutzung,
   * z.B. movie.fsk, wie ein Zugriff auf eine Objektvariable an und der
   * Nutzer der Klasse weiß gar nicht, dass der eigentliche Wert von einer
   * Methode kommt. Sinnvollerweise nennt man den Getter dann natürlich nicht
   * getFsk.
   */
  def fsk: FSK = fskVal
  /* Die Methode ageOK besteht nur aus einer einzigen Anweisung. Wie
   * in so einem Fall auch bei if, while, ... möglich, dürfen hier auch
   * die geschweiften Klammern weg gelassen werden.
   */
  def ageOK(age: Int): Boolean =
    return FSK.ageOK(fsk, age);
  // toString für einen Film (override wird in Moviestore erwähnt)
  override def toString(): String =
    return title + " (" + fsk + ")"
}