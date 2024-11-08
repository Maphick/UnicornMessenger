package com.mariiadeveloper.unicornmessenger.utils


import com.makashovadev.filmsearcher.domain.IsSuccess
import com.mariiadeveloper.unicornmessenger.data.dto.response.CheckJwtResponseDto


object Converter {



    fun convertApiToCheckJwtDto(checkJwtDto: CheckJwtResponseDto): CheckJwtResponseDto {
        val result =
            checkJwtDto.let {
                CheckJwtResponseDto(
                    errors = it.errors,
                    is_valid = it.is_valid

                )
            }
        return result
    }

    /*fun convertApiToIsSuccessDto(isSuccessDto: IsSuccessResponseDto): IsSuccess {
        val result =
            isSuccessDto.let {
                IsSuccess(
                   is_success = it.is_success
                )
            }
        return result
    }
*/


}