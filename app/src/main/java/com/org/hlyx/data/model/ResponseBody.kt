package com.org.hlyx.data.model

import android.os.Parcel
import android.os.Parcelable


class ResponseBody {
    data class ResLeftRight(
        val desc: String,
        val descColor: String,
        val imageUrl: String,
        val title: String,
        val titleColor: String
    )

    data class ResLeftRightText(
        val desc: String?,
        val descColor: String?,
        val title: String?,
        val titleColor: String?
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(desc)
            parcel.writeString(descColor)
            parcel.writeString(title)
            parcel.writeString(titleColor)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<ResLeftRightText> {
            override fun createFromParcel(parcel: Parcel): ResLeftRightText {
                return ResLeftRightText(parcel)
            }

            override fun newArray(size: Int): Array<ResLeftRightText?> {
                return arrayOfNulls(size)
            }
        }
    }
}