package com.ndoudou.tp1.data.remote.dto
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Support(
    val text: String,
    val url: String
) : Parcelable