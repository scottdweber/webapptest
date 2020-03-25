package com.proquest.interview.phonebook

import com.proquest.interview.util.DatabaseUtil

fun main(args: Array<String>) {
    DatabaseUtil.initDB() // Do not remove this line, it creates the simulated database.
    // Context: the basic idea is that the phone book lives in ("is persisted to") an
    // SQL database.  For this exercise, we're using a simulated database (that really just
    // lives in memory), but pretend that it is a "real", persisted-on-disk database.
    //
    // But ALL of the data should live in-memory in an instance of
    // the PhoneBookImpl class, AS WELL AS being persisted to the database.
    // TODO: 1. Create these new Person objects, and put them in both PhoneBook and Database.
    //    John Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI
    //    Cynthia Smith, (824) 128-8758, 875 Main St, Ann Arbor, MI
    val phonebook = PhoneBookImpl(object : PersistentStorage {
        override fun addPersonAsync(newPerson: Person) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun findPerson(firstName: String, lastName: String): Person? {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    })
    phonebook.addPerson(Person("John Smith", "(248) 123-4567", "1234 Sand Hill Dr, Royal Oak, MI"))
    phonebook.addPerson(Person("Cynthia Smith", "(824) 128-8758", "875 Main St, Ann Arbor, MI"))

    //
    // TODO 2: Print the whole phone book to System.out.
    //
    // TODO 3: Find Cynthia Smith and print just her entry to System.out.
    println(phonebook.findPerson("Cynthia", "Smith"))
    //
    // Hint: you don't have to implement these features strictly in that order.
}
