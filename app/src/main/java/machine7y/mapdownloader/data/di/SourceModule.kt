package machine7y.mapdownloader.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import machine7y.mapdownloader.data.source.InternalMemorySourceImpl
import machine7y.mapdownloader.domain.source.InternalMemorySource

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    abstract fun bindInternalMemorySource(source: InternalMemorySourceImpl): InternalMemorySource
}
