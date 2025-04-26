package id.suspendfun.lib_network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.suspendfun.lib_network.interceptor.ConnectivityInterceptor
import id.suspendfun.lib_network.interceptor.ConnectivityInterceptorImpl
import id.suspendfun.lib_network.service.ApiService
import id.suspendfun.lib_network.service.ApiServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleInjection {
    @Provides
    @Singleton
    fun provideConnectivityInterceptor(
        @ApplicationContext context: Context,
    ): ConnectivityInterceptor = ConnectivityInterceptorImpl(context)

    @Provides
    @Singleton
    fun provideApiService(
        connectivityInterceptor: ConnectivityInterceptor,
    ): ApiService = ApiServiceImpl(connectivityInterceptor)
}