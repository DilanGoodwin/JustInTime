package com.dbad.justintime.f_login_register.domain.use_case

import com.dbad.justintime.f_login_register.data.UserRepositoryTestingImplementation
import com.dbad.justintime.f_login_register.domain.model.User
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import com.dbad.justintime.f_login_register.domain.util.PasswordErrors
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RepositoryUseCaseTests {

    private val validEmail = "email@email.com"
    private val validPassword = "MyP@ssw0rds"
    private lateinit var useCases: UserUseCases

    private val users: List<User> = listOf(
        User(uid = 0, email = "email@email.com", password = "MyP@ssw0rds"),
        User(uid = 1, email = "justintime@justintime.com", password = "Th!sSh0uldB3Secure")
    )


    @Before
    fun reset() {
        val userRepo: UserRepository = UserRepositoryTestingImplementation(users = users)
        useCases = UserUseCases(
            getUser = GetUser(repository = userRepo),
            upsertUser = UpsertUser(repository = userRepo),
            getEmployeeKey = GetEmployeeKey(repository = userRepo),
            upsertEmployee = UpsertEmployee(repository = userRepo),
            getEmergencyContactKey = GetEmergencyContactKey(repository = userRepo),
            upsertEmergencyContact = UpsertEmergencyContact(repository = userRepo),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validatePhoneNumber = ValidatePhoneNumber(),
            validateDate = ValidateDate()
        )
    }

    @Test
    fun gettingUser() = runTest {
        var userReceived: User = useCases.getUser(user = User())
        assertEquals("Received user did not match the expected user", User(), userReceived)

        userReceived = useCases.getUser(user = User(email = validEmail))
        assertEquals("Received user did not match the expected user", User(), userReceived)

        userReceived = useCases.getUser(user = User(password = validPassword))
        assertEquals("Received user did not match the expected user", User(), userReceived)

        userReceived = useCases.getUser(user = User(email = validEmail))
        assertEquals("Received user did not match the expected user", userReceived, User())

        userReceived = useCases.getUser(user = User(email = validEmail, password = validPassword))
        assertEquals(
            "Received user did not match the expected user",
            User(uid = 0, email = validEmail, password = validPassword),
            userReceived
        )
    }

    @Test
    fun addingUser() = runTest {
        val listCurrentSize = users.size
        val newUser = User(email = "something@something.com", password = "S0mething!G0esHere")
        useCases.upsertUser(user = newUser)

        val receivedUser = useCases.getUser(user = newUser)
        assertNotEquals("Received user matches user when it should not", newUser, receivedUser)
        assertEquals("uid number has not been incremented", listCurrentSize, receivedUser.uid)
        assertEquals("email does not match", newUser.email, receivedUser.email)
        assertEquals("password does not match", newUser.password, receivedUser.password)
    }

    @Test
    fun validatingEmailAddresses() {
        var invalidEmail = ""
        assertFalse("", useCases.validateEmail(email = invalidEmail))

        invalidEmail = "emailatemail.com"
        assertFalse("", useCases.validateEmail(email = invalidEmail))

        invalidEmail = "email@email"
        assertFalse("", useCases.validateEmail(email = invalidEmail))

        invalidEmail = "email.email@email"
        assertFalse("", useCases.validateEmail(email = invalidEmail))

        invalidEmail = "email.email@email.co.uk"
        assertTrue("", useCases.validateEmail(email = invalidEmail))

        assertTrue("", useCases.validateEmail(email = validEmail))
    }

    @Test
    fun validatingPasswords() {
        var invalidPassword = ""
        assertEquals(
            "",
            PasswordErrors.PASSWORD_BLANK,
            useCases.validatePassword(password = invalidPassword)
        )

        invalidPassword = "SOMETHING"
        assertEquals(
            "",
            PasswordErrors.PASSWORD_NO_LOWERCASE,
            useCases.validatePassword(password = invalidPassword)
        )

        invalidPassword = "something"
        assertEquals(
            "",
            PasswordErrors.PASSWORD_NO_UPPERCASE,
            useCases.validatePassword(password = invalidPassword)
        )

        invalidPassword = "SomeThing"
        assertEquals(
            "",
            PasswordErrors.PASSWORD_NO_DIGIT,
            useCases.validatePassword(password = invalidPassword)
        )

        invalidPassword = "S0meTh1ng"
        assertEquals(
            "",
            PasswordErrors.PASSWORD_NO_SYMBOLS,
            useCases.validatePassword(password = invalidPassword)
        )

        invalidPassword = "S0m%Th1ng"
        assertEquals(
            "",
            PasswordErrors.PASSWORD_LENGTH_UNDER_10,
            useCases.validatePassword(password = invalidPassword)
        )

        invalidPassword = "S0m%Th1ngAnotherThing"
        assertEquals(
            "",
            PasswordErrors.PASSWORD_NONE,
            useCases.validatePassword(password = invalidPassword)
        )
    }

    @Test
    fun validatingPhoneNumbers() {
        var phoneNumber = ""
        assertFalse("", useCases.validatePhoneNumber(phone = phoneNumber))

        phoneNumber = "7665599200"
        assertTrue("", useCases.validatePhoneNumber(phone = phoneNumber))

        phoneNumber = "07665599200"
        assertTrue("", useCases.validatePhoneNumber(phone = phoneNumber))

        phoneNumber = "+447665599200"
        assertTrue("", useCases.validatePhoneNumber(phone = phoneNumber))

        phoneNumber = "+4407665599200"
        assertTrue("", useCases.validatePhoneNumber(phone = phoneNumber))
    }

    @Test
    fun validatingDate() {
    }
}