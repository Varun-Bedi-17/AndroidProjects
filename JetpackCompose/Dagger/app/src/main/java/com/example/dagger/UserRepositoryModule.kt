package com.example.dagger

import dagger.Module
import dagger.Provides
import javax.inject.Named


/**
 * If the way of creating objects is different like we have to create retrofit builder object or
 * we have to create objects of classes that is extended from interface, then modules are used to
 * used to provide instance of these classes.
 */
@Module
class UserRepositoryModule(private val retryCount : Int) {

    // @Provides tell Dagger how to create instances of the type that this function return.
    @Named("firebase")
    @Provides
    fun getFirebaseRepository() : UserRepository{
        return FireStoreRepository(retryCount)  // or suppose we need to do some logic here to create room database builder.
    }

    // To create instance of those classes which have @Inject in constructor, we can pass directly as a param of function.
    @Named("sql")       // When we have two or more functions which return same type in module. Then we have to pass same annotation where the returning class object is created
    @Provides
    fun getSQLRepository(sqlRepository : SQLRepository) : UserRepository{
        return sqlRepository  // or suppose we need to do some logic here to create room database builder.
    }
}