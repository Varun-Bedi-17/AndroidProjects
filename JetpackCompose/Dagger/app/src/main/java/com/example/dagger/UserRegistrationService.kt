package com.example.dagger

import javax.inject.Inject
import javax.inject.Named


/** But there are issues with this approach :
 * Testing - Unit testing can't be performed without reference of repo and email service.
 * Single Responsibility : Class does not doing any single work. It registers user and also create objects.
 * Lifetime of these objects  depend on lifetime of this class.
 * Extensible - These objects are hardcoded so if we want to change any of our functionality ,
 *               then we need to create another object.
 */
/*class UserRegistrationService {
    private val userRepository = UserRepository()
    private val emailService = EmailService()

    fun registerUser(email : String, pass : String){
        userRepository.createUser(email, pass)
        emailService.send(email, "noreply@gamil.com", "User registered")
    }
}*/


/**
 *  Constructor Injection
 *  We can inject these objects by using field and method injection also.
 */
/*class UserRegistrationService(private val userRepository: UserRepository,private val emailService: EmailService) {

    fun registerUser(email : String, pass : String){
        userRepository.createUser(email, pass)
        emailService.send(email, "noreply@gamil.com", "User registered")
    }
}*/


class UserRegistrationService @Inject constructor(
    @Named("firebase")private val userRepository: UserRepository,
    private val notificationService: NotificationService,
) {

    fun registerUser(email : String, pass : String){
        userRepository.createUser(email, pass)
        notificationService.send(email, "noreply@gamil.com", "User registered")
    }
}
