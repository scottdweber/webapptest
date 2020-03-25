package com.proquest.interview.phonebook

class PhoneBookImpl : PhoneBook {
    private val people = mutableListOf<Person>()

    override fun addPerson(newPerson: Person) {
        people.add(newPerson)
    }

    override fun findPerson(firstName: String, lastName: String): Person? {
        return people.firstOrNull { person -> person.name == "$firstName $lastName" }
    }
}