package com.mariiadeveloper.unicornmessenger.utils

import com.makashovadev.filmsearcher.domain.CheckJwt
import com.makashovadev.filmsearcher.domain.IsSuccess
import com.mariiadeveloper.unicornmessenger.data.dto.response.CheckJwtResponseDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.IsSuccessResponseDto


object Converter {



    fun convertApiToCheckJwtDto(checkJwtDto: CheckJwtResponseDto): CheckJwt {
        val result =
            checkJwtDto.let {
                CheckJwt(
                    errors = it.errors,
                    is_valid = it.is_valid

                )
            }
        return result
    }

    fun convertApiToIsSuccessDto(isSuccessDto: IsSuccessResponseDto): IsSuccess {
        val result =
            isSuccessDto.let {
                IsSuccess(
                   is_success = it.is_success
                )
            }
        return result
    }



}