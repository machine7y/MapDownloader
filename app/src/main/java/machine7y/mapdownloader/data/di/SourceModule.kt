package machine7y.mapdownloader.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import machine7y.mapdownloader.data.source.InternalMemorySourceImpl
import machine7y.mapdownloader.data.source.RegionSourceImpl
import machine7y.mapdownloader.domain.source.InternalMemorySource
import machine7y.mapdownloader.domain.source.RegionSource

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    abstract fun bindInternalMemorySource(source: InternalMemorySourceImpl): InternalMemorySource

    @Binds
    abstract fun bindRegionSource(source: RegionSourceImpl): RegionSource
}
