package samples

import groovy.lang.Tuple
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.regex.Matcher
import java.util.regex.Pattern

object P17_ReactiveCalculator : SampleInterface {

    override fun execute() {
        println("Initial Out put with a = 15, b = 10")
        var calculator:ReactiveCalculator = ReactiveCalculator(15,10)

        println("Enter a = <number> or b = <number> in separate lines")
        var line:String?
        do {
            line = readLine();
            calculator.handleInput(line)
        } while (line!= null && !line.toLowerCase().contains("exit"))
    }



    private class ReactiveCalculator(a : Int, b : Int){
        var values : Pair<Int, Int>
        var subjectAdd   : Subject<Pair<Int, Int>>
        var subjectSub   : Subject<Pair<Int, Int>>
        var subjectMulti : Subject<Pair<Int, Int>>
        var subjectDiv   : Subject<Pair<Int, Int>>
        var subjectCalc  : Subject<ReactiveCalculator>

        init {
            values = Pair(a, b)

            subjectAdd = PublishSubject.create()
            subjectAdd.map { it.first + it.second}.subscribe { println("Add = ${it}") }

            subjectSub = PublishSubject.create()
            subjectSub.map { it.first - it.second}.subscribe { println("Sub = ${it}")}

            subjectMulti = PublishSubject.create()
            subjectMulti.map { it.first * it.second }.subscribe { println("Multi = ${it}") }

            subjectDiv = PublishSubject.create()
            subjectDiv.map { it.first / it.second * 1.0 }.subscribe { println("Div = ${it}") }

            subjectCalc = PublishSubject.create()
            subjectCalc.map {
                with(it)
                {
                    calculateAdd(it.values)
                    calculateSub(it.values)
                    calculateMulti(it.values)
                    calculateDiv(it.values)
                }
            }.subscribe()
        }

        fun handleInput(inputLine : String?) {
            if(!inputLine.equals("exit")) {
                val pattern: Pattern = Pattern.compile("([a|b])(?:\\s)?=(?:\\s)?(\\d*)")
                var a: Int? = null
                var b: Int? = null

                val matcher: Matcher = pattern.matcher(inputLine)
                if (matcher.matches() && matcher.group(1) != null &&  matcher.group(2) != null) {
                    if(matcher.group(1).toLowerCase().equals("a")) {
                        a = matcher.group(2).toInt()
                    } else if(matcher.group(1).toLowerCase().equals("b")){
                        b = matcher.group(2).toInt()
                    }
                }

                when {
                    a != null && b != null -> modifyNumbers(a, b)
                    a != null -> modifyNumbers(a = a)
                    b != null -> modifyNumbers(b = b)
                    else -> println("Invalid Input")
                }
            }
        }

        fun calculateAdd(values : Pair<Int, Int>){
            subjectAdd.onNext(values)
        }

        fun calculateSub(values : Pair<Int, Int>){
            subjectSub.onNext(values)
        }

        fun calculateMulti(values : Pair<Int, Int>){
            subjectMulti.onNext(values)
        }

        fun calculateDiv(values : Pair<Int, Int>){
            subjectDiv.onNext(values)
        }

        fun modifyNumbers (a:Int = values.first, b: Int = values.second) {
            values = Pair(a,b)
            subjectCalc.onNext(this)
        }
    }
}