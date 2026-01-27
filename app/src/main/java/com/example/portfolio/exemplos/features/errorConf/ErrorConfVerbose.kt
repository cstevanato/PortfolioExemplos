package com.example.portfolio.exemplos.features.errorConf

data class User( val name: String )

private sealed interface UserResult {
    data class Success(val user: User) : UserResult
    data object NetworkError: UserResult
    data object ParsingError: UserResult
}

// highly Verbosity
private fun  getUserSealed() : UserResult {
    val user = fetchUser() ?: return UserResult.NetworkError
    val parsedUser = user.parseUser() ?: return UserResult.ParsingError
    return UserResult.Success(parsedUser)
}


fun usageSealed() {
    when (val result = getUserSealed()) {
        UserResult.NetworkError -> println("Network error")
        UserResult.ParsingError -> println("Parsing error")
        is UserResult.Success -> {
            println("Success: ${result.user.name}")
        }
    }
}


private fun User.parseUser(): User? {
    return this
}

private fun fetchUser(): User? {
    return User("John")
}
