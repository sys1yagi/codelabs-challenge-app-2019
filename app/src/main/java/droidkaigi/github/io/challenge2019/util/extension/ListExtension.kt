package droidkaigi.github.io.challenge2019.util.extension

fun <T> List<T>.swapFirst(newItem: T, indexFinder: (T) -> Boolean): List<T> {
    return mapIndexedNotNull { index, t ->
        if (indexFinder(t)) {
            index
        } else {
            null
        }
    }.firstOrNull()?.let { index ->
        toMutableList()
            .apply {
                removeAt(index)
                add(index, newItem)
            }
    } ?: this
}

