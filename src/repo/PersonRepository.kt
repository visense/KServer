package repo

import java.util.concurrent.CopyOnWriteArraySet
import java.util.concurrent.atomic.AtomicInteger

object PersonRepository {
    private val idCounter = AtomicInteger()
    private val persons = CopyOnWriteArraySet<Person>()
    fun add(person: Person): Person {
        if (persons.contains(person)) {
            return persons.find { it == person }!!
        }
        person.id = idCounter.incrementAndGet()
        persons.add(person)
        return person
    }

    fun get(id: String) =
        persons.find { it.id.toString() == id } ?: throw IllegalArgumentException("没有此${id}的实例")

    fun get(id: Int) = get(id.toString())

    fun getAll() = persons.toList()
    fun remove(person: Person) {
        if (!persons.contains(person)) throw IllegalArgumentException("${person}不在该存储库")
        persons.remove(person)
    }

    fun remove(id: String) = persons.remove(get(id))

    fun remove(id: Int) = persons.remove(get(id))

}
