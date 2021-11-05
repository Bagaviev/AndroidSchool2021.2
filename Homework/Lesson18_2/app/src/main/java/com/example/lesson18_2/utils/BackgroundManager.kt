package com.example.lesson18_2.utils

/**
 * @author Bulat Bagaviev
 * @created 30.10.2021
 */

class BackgroundManager {
    // Везде надо было работать с Callable, что при использовании ExecutorService приводило к блокирующему вызову. Не было времени самописный Callback / CompletableFuture делать
    // Также непонятно то делать с Context, не пробрасывать же его сюда.
}