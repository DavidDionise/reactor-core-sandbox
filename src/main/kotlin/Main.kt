import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.Error

fun main() {
    Mono.zip(listOf(findUserById(1),findUserById(2),findUserById(3), findUserById(1))) {users ->
        Flux.fromArray(users as Array<Object>)
            .doOnNext{user ->
                println((user as User).firstName)
            }
            .subscribe()
        println((users[0] as User).firstName)
    }
        .subscribe()

//    findUsers()
//        .map { user ->
//            if(user.id == 5)
//                throw Throwable("Ya dun goofed")
//            else
//                user
//        }
////        .doOnError{error ->
////            println(error.message)
////        }
//        .onErrorContinue{error,user ->
//            println(error.message)
//            println("Error user: ${(user as User).firstName}")
//        }
//        .doOnNext{user ->
//            println("Successful User Call: ${(user as User).firstName}")
//        }
//        .subscribe()


}

fun findUsers(): Flux<User> {
    return Flux.fromIterable(users)
}

fun findUserById(id: Int): Mono<User> {
    val user = users.find { it.id == id }
    return if (user != null) {
        Mono.just(user)
    } else {
        Mono.error(Throwable("No user found with id $id"))
    }
}

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int
)

val users = listOf(
    User(
        1, "David", "Dio", 34
    ),
    User(
        2, "Sara", "Hart", 38
    ),
    User(
        3, "Lydia", "Cournoyer", 33
    ),
    User(
        4, "Henri", "Dio", 0
    )
)
