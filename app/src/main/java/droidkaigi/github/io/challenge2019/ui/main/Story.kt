package droidkaigi.github.io.challenge2019.ui.main

import android.os.Parcelable
import droidkaigi.github.io.challenge2019.data.entity.ItemEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Story(val item: ItemEntity, val alreadyRead: Boolean): Parcelable
