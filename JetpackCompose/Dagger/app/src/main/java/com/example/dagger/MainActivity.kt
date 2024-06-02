package com.example.dagger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dagger.ui.theme.DaggerTheme
import javax.inject.Inject

const val TAG = "Dagger"

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var userRegistrationService2: UserRegistrationService

    @Inject
    lateinit var emailService2: EmailService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            manualDependencyInjection()
            dependencyInjectionUsingDagger()
        }
    }

    private fun manualDependencyInjection() {
        /**
         * Manual dependency injection - Creating objects manually.
         * Issues with it :
         * -> Lot of boiler plate code. Code duplication on creating another instance in another part of code.
         * -> Dependencies have to  be declared in order.
         * -> Difficult to reuse objects.
         */
        /*val userRepository = UserRepository()
        val emailService = EmailService()
        val userRegistration = UserRegistrationService(userRepository, emailService)
        userRegistration.registerUser("vbjksbv@mail", "1234")*/
    }

    private fun dependencyInjectionUsingDagger() {

        /**
         * Create an instance of the userRegistrationComponent graph
         */
        /*val userRegistrationComponent = DaggerUserRegistrationComponent.builder().build()

        val userRegistrationService = userRegistrationComponent.getUserRegistrationService()
        val emailService = userRegistrationComponent.getEmailService()*/

        /**
         * Using Inject method
         */
        /*val userRegistrationComponent2 = DaggerUserRegistrationComponent.builder().build()
        userRegistrationComponent2.inject(this)
        // Now userRegistrationService2 has been initialized
        userRegistrationService2.registerUser("njknc@mail", "12")*/

        /**
         * If we want to provide values to any module at runtime then we need to use component factory.
         */
        val userRegistrationComponent3 = DaggerUserRegistrationComponent
            .builder()
            .userRepositoryModule(UserRepositoryModule(3))
            .build()
        userRegistrationComponent3.inject(this)
        // Now userRegistrationService2 has been initialized
        userRegistrationService2.registerUser("njknc@mail", "12")
    }
}

