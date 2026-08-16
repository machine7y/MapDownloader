package machine7y.mapdownloader.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import machine7y.mapdownloader.presentation.navigation.Router
import machine7y.mapdownloader.presentation.navigation.RouterImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RouterModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindRouter(router: RouterImpl): Router
}
