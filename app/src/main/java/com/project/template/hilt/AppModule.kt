package com.project.template.hilt

import com.project.template.network.ApiWebService
import com.project.template.repo.login.LoginRds
import com.project.template.repo.login.LoginRepo
import com.project.template.repo.registration.RegistrationRds
import com.project.template.repo.registration.RegistrationRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun provideLoginRemoteDataSource(apiWebService: ApiWebService): LoginRds {
        return LoginRds(apiWebService)
    }

    @Provides
    fun provideLoginRepository(loginRds: LoginRds): LoginRepo {
        return LoginRepo(loginRds)
    }

    @Provides
    fun provideRegistrationRemoteDataSource(apiWebService: ApiWebService): RegistrationRds {
        return RegistrationRds(apiWebService)
    }

    @Provides
    fun provideRegistrationRepository(rds: RegistrationRds): RegistrationRepo {
        return RegistrationRepo(rds)
    }
}