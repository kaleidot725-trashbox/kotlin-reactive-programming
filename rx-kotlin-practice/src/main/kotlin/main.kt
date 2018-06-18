fun main(args: Array<String>) {
    while(true){
        try {
            println("Observable Samples")
            println("000 Exit")
            println("001 Observables")
            println("002 ReactiveEvenOdd")
            println("003 ReactiveCalculator")
            print(">> ")

            val no = readLine()?.toInt()
            println()

            if (no == 0)
                break

            if (no == 1)
                samples.P15_Observables.execute()

            if (no == 2)
                samples.P16_ReactiveEvenOdd.execute()

            if (no == 3)
                samples.P17_ReactiveCalculator.execute()
        }
        catch (e :Exception)
        {
            println(e)
        }
    }
}
