package com.example.dagger

import dagger.Component


/**
 * @Component makes Dagger create a graph of dependencies
 * Inside the @Component interface, you can define functions that return instances of the classes you need (
  */
@Component(modules = [UserRepositoryModule::class, NotificationServiceModule::class])
interface UserRegistrationComponent {

    fun getUserRegistrationService() : UserRegistrationService
    fun getEmailService() : EmailService

    /*
    If we don't want to make multiple functions in this component. Then, we can create it using this function in mainActivity.
     */
    fun inject(mainActivity: MainActivity)
}