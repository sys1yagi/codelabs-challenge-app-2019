package droidkaigi.github.io.challenge2019.ui.main

import android.os.Parcelable
import droidkaigi.github.io.challenge2019.data.api.response.Item
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Story(val item: Item, val alreadyRead: Boolean): Parcelable
