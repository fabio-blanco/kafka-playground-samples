package br.com.globalbyte.sample.kafka.kfs.utils

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

private interface IndexedNameList {
    operator fun get(index: Int): String
    val size: Int
    val lastIndex: Int
}

object OddEventRandomizer {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun getRandomEvent(): String {
        val superHero = SuperHeroConstants[(0 .. SuperHeroConstants.lastIndex).random()]
        val programmingLanguage = LanguageConstants[(0 .. LanguageConstants.lastIndex).random()]

        return "$superHero is programming $programmingLanguage"
    }

    fun getTimedRandomEvent(): String =
        "${ ZonedDateTime.now( ZoneId.of("America/Sao_Paulo") ).format(formatter) }: ${getRandomEvent()}"
}

private object LanguageConstants: IndexedNameList {
    override fun get(index: Int): String = when(index) {
        0 -> "Kotlin"
        1 -> "Java"
        2 -> "C++"
        3 -> "C#"
        4 -> "Python"
        5 -> "JavaScript"
        6 -> "TypeScript"
        7 -> "Go"
        8 -> "Rust"
        9 -> "Swift"
        10 -> "PHP"
        11 -> "Ruby"
        12 -> "ShellScript"
        13 -> "Scala"
        14 -> "Groovy"
        15 -> "Clojure"
        16 -> "Haskell"
        17 -> "Cobol"
        18 -> "Fortran"
        19 -> "Perl"
        20 -> "Lua"
        else -> "Unknown"
    }

    override val size: Int
        get() = 21
    override val lastIndex: Int
        get() = 20
}

private object SuperHeroConstants: IndexedNameList {
    override fun get(index: Int): String = when(index) {
        0 -> "Superman"
        1 -> "Batman"
        2 -> "Aquaman"
        3 -> "Wolverine"
        4 -> "Flash"
        5 -> "Thor"
        6 -> "Spiderman"
        7 -> "Daredevil"
        8 -> "Wonder Woman"
        9 -> "Captain America"
        10 -> "Iron Man"
        11 -> "Hulk"
        12 -> "Green Lantern"
        13 -> "Ant Man"
        14 -> "Doctor Strange"
        15 -> "Scarlet Witch"
        16 -> "Black Widow"
        else -> "Unknown"
    }

    override val size: Int
        get() = 17
    override val lastIndex: Int
        get() = 16
}