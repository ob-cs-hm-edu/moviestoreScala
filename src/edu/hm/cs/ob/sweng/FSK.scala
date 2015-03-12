package edu.hm.cs.ob.sweng

/* Eine Enumeration sieht in Scala etwas ungewöhnlich aus */
object FSK extends Enumeration {

  // Der Typ der Werte
  type FSK = Value

  /* Die Werte (z.B. FSK0) und seine Stringrepräsentation (z.B. "Ab 0")
   * z.B. für toString.
   */
  val FSK0  = Value("Ab 0")
  val FSK6  = Value("Ab 6")
  val FSK12 = Value("Ab 12")
  val FSK16 = Value("Ab 16")
  val FSK18 = Value("Ab 18")

  /* Eine Methode des Objektes FSK, dem der FSK-Wert noch explizit übergeben
   * werden muss.
   */
  def ageOK(fsk: FSK, age: Int): Boolean =
    fsk match {
      case FSK0  => true
      case FSK6  => age >= 6
      case FSK12 => age >= 12
      case FSK16 => age >= 16
      case FSK18 => age >= 18
      case _     => false
    }
}
