package droidkaigi.github.io.challenge2019.util.extension

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ListExtensionKtTest {

    @Test
    fun swapFirstSuccess() {
        val result = listOf("a", "b", "c").swapFirst("d") { it == "b" }
        assertThat(result).isEqualTo(listOf("a", "d", "c"))
    }

    @Test
    fun swapFirstNotFound() {
        val result = listOf("a", "b", "c").swapFirst("d") { it == "e" }
        assertThat(result).isEqualTo(listOf("a", "b", "c"))
    }

    @Test
    fun swapFirstDouble() {
        val result = listOf("a", "b", "b").swapFirst("d") { it == "b" }
        assertThat(result).isEqualTo(listOf("a", "d", "b"))
    }
}
