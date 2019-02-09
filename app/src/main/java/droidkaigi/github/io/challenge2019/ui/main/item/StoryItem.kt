package droidkaigi.github.io.challenge2019.ui.main.item

import androidx.appcompat.widget.PopupMenu
import com.xwray.groupie.databinding.BindableItem
import droidkaigi.github.io.challenge2019.R
import droidkaigi.github.io.challenge2019.data.api.response.Item
import droidkaigi.github.io.challenge2019.databinding.ItemStoryBinding
import droidkaigi.github.io.challenge2019.ui.main.Story

class StoryItem(
    private val story: Story,
    private val onClickItem: ((Item) -> Unit) = {},
    private val onClickMenuItem: ((Item, Int) -> Unit) = { _, _ -> }
) : BindableItem<ItemStoryBinding>() {
    override fun getLayout() = R.layout.item_story

    override fun bind(binding: ItemStoryBinding, position: Int) {
        binding.alreadyRead = false
        binding.alreadyRead = story.alreadyRead
        binding.item = story.item
        binding.root.setOnClickListener {
            onClickItem(story.item)
        }
        binding.menuButton.setOnClickListener {
            PopupMenu(binding.menuButton.context, binding.menuButton).apply {
                inflate(R.menu.story_menu)
                setOnMenuItemClickListener { menuItem ->
                    val menuItemId = menuItem.itemId
                    when (menuItemId) {
                        R.id.copy_url,
                        R.id.refresh -> {
                            onClickMenuItem(story.item, menuItemId)
                            true
                        }
                        else -> false
                    }
                }
                show()
            }
        }
    }
}
