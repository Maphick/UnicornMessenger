package com.makashovadev.filmsearcher.domain

import android.annotation.SuppressLint
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

// класс IsSuccess, который мы кладем в интерактор
//@Parcelize
/*data class IsSuccess(
    var  is_success: Boolean = true
) : Parcelable {

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readBoolean(),
    ) {
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeBoolean(is_success)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IsSuccess> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): IsSuccess {
            return IsSuccess(parcel)
        }

        override fun newArray(size: Int): Array<IsSuccess?> {
            return arrayOfNulls(size)
        }
    }
}*/