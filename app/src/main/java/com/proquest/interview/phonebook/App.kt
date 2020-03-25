package com.proquest.interview.phonebook

import com.proquest.interview.util.DatabaseConnectionFactory
import com.proquest.interview.util.DatabaseUtil

fun main(args: Array<String>) {
    DatabaseUtil.initDB() // Do not remove this line, it creates the simulated database.

    val phonebook = PhoneBookImpl(PhoneBookPersistentStorage(DatabaseConnectionFactory()))
    phonebook.addPerson(Person("John Smith", "(248) 123-4567", "1234 Sand Hill Dr, Royal Oak, MI"))
    phonebook.addPerson(Person("Cynthia Smith", "(824) 128-8758", "875 Main St, Ann Arbor, MI"))

    //
    // TODO 2: Print the whole phone book to System.out.
    //
    println(phonebook.findPerson("Cynthia", "Smith"))
}
