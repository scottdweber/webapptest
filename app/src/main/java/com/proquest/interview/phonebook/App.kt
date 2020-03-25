package com.proquest.interview.phonebook

import com.proquest.interview.util.DatabaseConnectionFactory
import com.proquest.interview.util.DatabaseUtil
import kotlinx.coroutines.GlobalScope

fun main(args: Array<String>) {
    DatabaseUtil.initDB() // Do not remove this line, it creates the simulated database.

    val phonebook = PhoneBookImpl(PhoneBookPersistentStorage(DatabaseConnectionFactory(), GlobalScope))
    phonebook.addPerson(Person("John Smith", "(248) 123-4567", "1234 Sand Hill Dr, Royal Oak, MI"))
    phonebook.addPerson(Person("Cynthia Smith", "(824) 128-8758", "875 Main St, Ann Arbor, MI"))

    println("Full Phone Book:")
    phonebook.getAllPersons().forEach { person -> println(person) }

    println("")
    println("Cynthia Smith:")
    println(phonebook.findPerson("Cynthia", "Smith"))
}
