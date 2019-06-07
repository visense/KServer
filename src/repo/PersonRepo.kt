package repo

import java.util.concurrent.CopyOnWriteArraySet
import java.util.concurrent.atomic.AtomicInteger

object PersonRepo {
    private val idCounter = AtomicInteger()
    private val persons = CopyOnWriteArraySet<Person>()

    fun add(p: Person): Person {
        if (persons.contains(p)) {
            return persons.find { it == p }!!
        }
        p.id = idCounter.incrementAndGet()
        persons.add(p)
        return p
    }

    fun get(id: String) =
        persons.find { it.id.toString() == id }
            ?: throw IllegalArgumentException("找不到${id}的实体")

    fun get(id: Int) = get(id.toString())

    fun getAll() = persons.toList()

    fun remove(p: Person) {
        if (!persons.contains(p)) {
            throw IllegalArgumentException("没有在存储库中找到该人!")
        }
        persons.remove(p)
    }

    fun remove(id: String) = persons.remove(get(id))

    fun remove(id: Int) = persons.remove(get(id))

    fun clear() = persons.clear()

}