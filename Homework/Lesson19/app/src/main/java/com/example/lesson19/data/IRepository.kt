package com.example.lesson19.data

import com.example.lesson19.domain.api_entities.RequestMain
import io.reactivex.Single

/**
 * @author Bulat Bagaviev
 * @created 11.11.2021
 */

interface IRepository {
    fun loadDataAsync(): Single<RequestMain>
}