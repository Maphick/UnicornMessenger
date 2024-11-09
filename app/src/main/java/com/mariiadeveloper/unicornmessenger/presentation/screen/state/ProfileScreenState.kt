package com.mariiadeveloper.unicornmessenger.presentation.screen.state

sealed class ProfileScreenEvent{
    data class UserNameUpdate(val userName: String): ProfileScreenEvent()
    data class CityUpdate(val city: String): ProfileScreenEvent()
    data class DateOfBirthUpdated(val dateOfBirth: String): ProfileScreenEvent()
    data class AboutMeUpdated(val aboutMe: String): ProfileScreenEvent()
    data class AvatarUpdated(val avatar: String): ProfileScreenEvent()
    class SaveChangedPressed: ProfileScreenEvent()
}

data class ProfileScreenState(
    var avatar: String = "qerwewef",
    var userName: String = "Hank",
    val city: String = "Moscow",
    val dateOfBirth: String = "11.02.1980",
    val aboutMe: String = "...",
)
