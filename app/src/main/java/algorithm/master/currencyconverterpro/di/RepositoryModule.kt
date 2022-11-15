package algorithm.master.currencyconverterpro.di

import algorithm.master.currencyconverterpro.data.remote.repositoryimpl.CurrencyRepositoryImpl
import algorithm.master.currencyconverterpro.domain.repository.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by promasterguru on 15/11/2022.
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCurrencyConverterRepository(currencyRepo: CurrencyRepositoryImpl): CurrencyRepository
}
