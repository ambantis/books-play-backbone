
val tag1 = (1, "JavaScript")
val tag2 = (1, "Programming")
val tag3 = (2, "CoffeeScript")
val tag4 = (2, "Programming")
val tag5 = (3, "Scala")
val tag6 = (4, "Teddy Bear")
val tag7 = (4, "Hundred Acre Wood")
val tag8 = (4, "Fiction")
val tag9 = (5, "JavaScript")
val tag10 = (5, "Programming")

val tags = List(tag1, tag2, tag3, tag4, tag5, tag6, tag7, tag8, tag9, tag10)

val tagMap: Map[Int, List[String]] = tags.groupBy(_._1).mapValues(x => x.map(y => y._2))

case class Book(id: Int, title: String, author: String, released: String, image: String, keywords: List[String])

val b1 = Book(1, "JavaScript: The Good Parts", "Douglas Crockford",
"2008-05-08", "java_script_the_good_parts.png", List())

val b2 = Book(2, "The Little Book on CoffeeScript", "Alex MacCaw",
"2012-01-31", "the_little_book_on_coffeescript.png", List())

val b3 = Book(3, "Scala for the Impatient", "Cay S. Horstmann",
"2012-03-16", "scala_for_the_impatient.png", List())

val b4 = Book(4, "Guns, Germs, and Steel", "Jared Diamond",
"1999-04-01", "guns_germs_and_steel.png", List())

val b5 = Book(5, "Eloquent_JavaScript", "Marijn Haverbeke",
"2011-02-03", "eloquent_javascript.png", List())

val books = List(b1, b2, b3, b4, b5)


