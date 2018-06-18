package samples

import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable

object P15_Observables : SampleInterface {
    override fun execute(){
        var list:MutableList<Any> = mutableListOf("One", 2, "Three", "Four", 4.5, "Five", 6.0f) // 1
        var observable = list.toObservable();
        observable.subscribeBy(
                onNext = { println(it) },
                onError =  { it.printStackTrace() },
                onComplete = { println("Done!") }
        )
        println()
    }
}