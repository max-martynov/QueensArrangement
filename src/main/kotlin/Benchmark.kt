import space.kscience.plotly.*
import space.kscience.plotly.models.*


class Benchmark {

    fun drawStatisticInPlot() {
        val trace1 = Scatter {
            x(4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80)
            y(65, 25, 3, 5, 4, 5, 6, 6, 8, 8, 9, 9, 15, 15, 16, 17, 28, 13, 18, 16, 17, 25, 27, 28, 27, 26, 25, 104, 89, 92, 49, 78, 32, 45, 80, 92, 59, 55, 153, 66, 51, 108, 132, 112, 117, 214, 151, 72, 83, 281, 224, 221, 138, 220, 241, 265, 765, 571, 463, 384, 292, 312, 459, 489, 440, 614, 529, 1219, 323, 402, 368, 605, 619, 1302, 482, 594, 571)
        }

        val plot = Plotly.plot {
            traces(trace1)

            layout {
                title = "Time needed for finding a valid arrangement on the board of size n"
                xaxis {
                    title = "n"
                    type = AxisType.linear
                    autorange = true
                }
                yaxis {
                    title = "time, ms"
                    type = AxisType.linear
                    autorange = true
                }
            }
        }
        plot.makeFile()
    }

}