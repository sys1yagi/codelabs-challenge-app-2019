package droidkaigi.github.io.challenge2019.ui.main

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import droidkaigi.github.io.challenge2019.R
import droidkaigi.github.io.challenge2019.data.db.ArticlePreferences.Companion.saveArticleIds
import droidkaigi.github.io.challenge2019.databinding.ActivityMainBinding
import droidkaigi.github.io.challenge2019.ui.main.item.StoryItem
import droidkaigi.github.io.challenge2019.ui.story.StoryActivity
import org.koin.androidx.viewmodel.ext.viewModel

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE = 0x11
        private const val STATE_STORIES = "stories"
    }

    private val adapter = GroupAdapter<ViewHolder>()

    private val viewModel by viewModel<MainViewModel>()

    val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val itemDecoration = DividerItemDecoration(
            binding.itemRecycler.context,
            DividerItemDecoration.VERTICAL
        )
        binding.itemRecycler.addItemDecoration(itemDecoration)

        binding.itemRecycler.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadTopStories(true)
        }

        val savedStories = savedInstanceState?.let { bundle ->
            bundle.getString(STATE_STORIES)?.let { itemsJson ->
                // itemsJsonAdapter.fromJson(itemsJson)
            }
        }

//        if (savedStories != null) {
//            storyAdapter.stories = savedStories.toMutableList()
//            storyAdapter.alreadyReadStories = ArticlePreferences.getArticleIds(this@MainActivity)
//            storyAdapter.notifyDataSetChanged()
//            return
//        }

        viewModel.viewState().observe({ lifecycle }) {
            when (it) {
                MainViewModel.ViewState.NotProgress -> {
                    binding.progress.isVisible = false
                    binding.swipeRefresh.isRefreshing = false
                }
                MainViewModel.ViewState.Progress -> {
                    binding.progress.isVisible = true
                }
                is MainViewModel.ViewState.Error -> {
                    binding.progress.isVisible = false
                    binding.swipeRefresh.isRefreshing = false
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.topStories().observe({ lifecycle }) {
            adapter.clear()
            adapter.addAll(it.map {
                StoryItem(
                    it,
                    { item ->
                        startActivityForResult(
                            StoryActivity.createIntent(this, item),
                            REQUEST_CODE
                        )
                    },
                    { item, menuItemId ->
                        when (menuItemId) {
                            R.id.copy_url -> {
                                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                clipboard.primaryClip = ClipData.newPlainText("url", item.url)
                            }
                            R.id.refresh -> {
                                viewModel.reloadItem(item)
                            }
                        }
                    },
                    // TODO
                    false
                )
            })
        }

        viewModel.loadTopStories()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.getLongExtra(StoryActivity.READ_ARTICLE_ID, 0L)?.let { id ->
                    if (id != 0L) {
                        saveArticleIds(this, id.toString())
//                        storyAdapter.alreadyReadStories = ArticlePreferences.getArticleIds(this)
//                        storyAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.exit -> {
                this.finish()
                return true
            }
            R.id.refresh -> {
                binding.swipeRefresh.isRefreshing = true
                 viewModel.loadTopStories(true)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
//        outState.apply {
//            putString(STATE_STORIES, itemsJsonAdapter.toJson(storyAdapter.stories))
//        }

        super.onSaveInstanceState(outState)
    }
}
