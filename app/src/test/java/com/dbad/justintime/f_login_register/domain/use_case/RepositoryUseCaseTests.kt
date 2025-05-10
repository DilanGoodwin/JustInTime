package com.dbad.justintime.f_login_register.domain.use_case

import com.dbad.justintime.core.domain.use_case.ValidateDate
import com.dbad.justintime.core.domain.use_case.ValidateEmail
import com.dbad.justintime.core.domain.use_case.ValidatePassword
import com.dbad.justintime.core.domain.use_case.ValidatePhoneNumber
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_login_register.data.UserRepositoryTestingImplementation
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import com.dbad.justintime.f_login_register.domain.util.PasswordErrors
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RepositoryUseCaseTests {

    private val currentDate = "05/03/2025"
    private val validEmail = "email@email.com"
    private val validPassword = "MyP@ssw0rds"
    private lateinit var useCases: UserUseCases

    private val users: List<User> = listOf(
        User(
            uid = User.generateUid(email = validEmail),
            email = validEmail
        ),
        User(
            uid = User.generateUid(email = "justintime@justintime.com"),
            email = "justintime@justintime.com"
        )
    )


    @Before
    fun reset() {
        val userRepo: UserRepository = UserRepositoryTestingImplementation(users = users)
        useCases = UserUseCases(
            getUser = GetUser(repository = userRepo),
            upsertUser = UpsertUser(repository = userRepo),
            getEmployee = GetEmployee(repository = userRepo),
            upsertEmployee = UpsertEmployee(repository = userRepo),
            getEmergencyContact = GetEmergencyContact(repository = userRepo),
            upsertEmergencyContact = UpsertEmergencyContact(repository = userRepo),
            updateLocalDatabase = UpdateLocalDatabase(repository = userRepo),
            deleteTmpUser = DeleteTmpUser(repo = userRepo),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validatePhoneNumber = ValidatePhoneNumber(),
            validateDate = ValidateDate(),
        )
    }

    @Test
    fun gettingUser() = runTest {
        var userReceived: User = useCases.getUser(user = User()).first()
        assertEquals("Received user did not match the expected user", User(), userReceived)

        userReceived = useCases.getUser(user = User(email = validEmail)).first()
        assertEquals("Received user did not match the expected user", User(), userReceived)

        userReceived = useCases.getUser(user = User(email = validEmail)).first()
        assertEquals("Received user did not match the expected user", userReceived, User())

        userReceived =
            useCases.getUser(user = User(uid = User.generateUid(email = validEmail))).first()
        assertEquals(
            "Received user did not match the expected user",
            User(
                uid = User.generateUid(email = validEmail),
                email = validEmail
            ),
            userReceived
        )
    }

    @Test
    fun addingUser() = runTest {
        val newUser = User(
            uid = User.generateUid(email = "something@something.com"),
            email = "something@something.com",
        )
        useCases.upsertUser(user = newUser)

        val receivedUser: User = useCases.getUser(user = newUser).first()
//        assertEquals("Received user matches user when it should not", newUser, receivedUser)
        assertEquals("email does not match", newUser.email, receivedUser.email)
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
            "", PasswordErrors.PASSWORD_BLANK, useCases.validatePassword(password = invalidPassword)
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
            "", PasswordErrors.PASSWORD_NONE, useCases.validatePassword(password = invalidPassword)
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
    fun validatingDate_InvalidDateValues() {
        var invalidDateString = ""
        assertFalse(
            useCases.validateDate(
                currentDate = currentDate, dateToCheck = invalidDateString
            )
        )
        assertFalse(
            useCases.validateDate(
                currentDate = invalidDateString, dateToCheck = currentDate
            )
        )

        invalidDateString = "03/03"
        assertFalse(
            useCases.validateDate(
                currentDate = currentDate, dateToCheck = invalidDateString
            )
        )
        assertFalse(
            useCases.validateDate(
                currentDate = invalidDateString, dateToCheck = currentDate
            )
        )

        invalidDateString = "00/03/2009"
        assertFalse(
            useCases.validateDate(
                currentDate = currentDate, dateToCheck = invalidDateString
            )
        )
        assertFalse(
            useCases.validateDate(
                currentDate = invalidDateString, dateToCheck = currentDate
            )
        )

        invalidDateString = "01/00/2009"
        assertFalse(
            useCases.validateDate(
                currentDate = currentDate, dateToCheck = invalidDateString
            )
        )
        assertFalse(
            useCases.validateDate(
                currentDate = invalidDateString, dateToCheck = currentDate
            )
        )

        invalidDateString = "01/01/0000"
        assertFalse(
            useCases.validateDate(
                currentDate = currentDate, dateToCheck = invalidDateString
            )
        )
        assertFalse(
            useCases.validateDate(
                currentDate = invalidDateString, dateToCheck = currentDate
            )
        )
    }

    @Test
    fun validatingDate_InvalidDates() {
        // Tests Invalid Dates
        var invalidDateString = "03/03/2010"
        assertFalse(
            useCases.validateDate(
                currentDate = currentDate, dateToCheck = invalidDateString
            )
        )
        assertFalse(
            useCases.validateDate(
                currentDate = invalidDateString, dateToCheck = currentDate
            )
        )

        invalidDateString = "12/12/2015"
        assertFalse(
            useCases.validateDate(
                currentDate = currentDate, dateToCheck = invalidDateString
            )
        )
        assertFalse(
            useCases.validateDate(
                currentDate = invalidDateString, dateToCheck = invalidDateString
            )
        )
    }

    @Test
    fun validatingDate_ValidDates() {
        var invalidDateString = "03/03/2009"
        assertTrue(
            useCases.validateDate(
                currentDate = currentDate, dateToCheck = invalidDateString
            )
        )

        invalidDateString = "20/05/2002"
        assertTrue(
            useCases.validateDate(
                currentDate = currentDate, dateToCheck = invalidDateString
            )
        )
    }
}