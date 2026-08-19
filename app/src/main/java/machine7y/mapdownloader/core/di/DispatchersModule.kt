package machine7y.mapdownloader.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import machine7y.mapdownloader.core.dispatchers.DispatchersProviderImpl
import machine7y.mapdownloader.core.dispatchers.DispatcherProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatchersModule {

    @Binds
    @Singleton
    abstract fun bindDispatchersProvider(provider: DispatchersProviderImpl): DispatcherProvider
}
