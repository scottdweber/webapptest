package com.proquest.interview.phonebook

class PhoneBookImpl(
    private val database: PersistentStorage
) : PhoneBook {
    private val people = mutableListOf<Person>()

    override fun addPerson(newPerson: Person) {
        database.addPersonAsync(newPerson)
        people.add(newPerson)
    }

    override fun findPerson(firstName: String, lastName: String): Person? {
        return people.firstOrNull { person -> person.name == "$firstName $lastName" } ?:
                database.findPerson(firstName, lastName)
    }
}