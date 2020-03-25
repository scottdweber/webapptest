package com.proquest.interview.phonebook

interface PhoneBook {
    fun findPerson(firstName: String, lastName: String): Person?
    fun addPerson(newPerson: Person)
}