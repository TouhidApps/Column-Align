package com.touhidapps.align

import java.util.regex.Matcher
import java.util.regex.Pattern


class MyStringProcess {

    /**
     * TODO
     *
     * Make multiple space inside code convert to single space
     * Get full line if selected half line
     *
     */

    fun getResultAlignedArray(myMultiLineString: String): ArrayList<String> {

        if (!myMultiLineString.contains("\n")) {
            return arrayListOf()
        }

        // Check any array item starts with white space or not
        val rmvEmptyLineArray = removeEmptyLineFromArray(myMultiLineString.split("\n"))

        val mData = getAllPreSpacesEqual(rmvEmptyLineArray)


        val specialChar = "#"
        val s = arrayListOf<String>()
        val t = arrayListOf<String>()


        // Insert special character to string value (inside of double quote) part to ignore formatting them
        mData.forEachIndexed { index, str ->

            var mStr = str

            if (str.contains("\"")) {

                val p: Pattern = Pattern.compile("\"([^\"]*)\"")
                val m: Matcher = p.matcher(str)

                while (m.find()) { // If multiple section of double quote it will loop

                    val a = m.group(0).replace(" ", "#")

                    println(a + "--" + m.start() + "--" + m.end())

                    mStr = str.replaceRange(m.start(), m.end(), a)

                }
            }

            s.add(mStr)
            t.add(mStr)

        }


        // Find most space contained string
        var mostSpaceString = ""

        s.forEachIndexed { index, item ->
            if (index == 0) {
                mostSpaceString = item
            } else {
                if (s[index - 1].split(" ").count() < item.split(" ").count()) {
                    mostSpaceString = item
                }
            }
        }

        //    println(mostSpaceString)
        //    println(bigIndex)


        // By column, used max column contained string
        // This is only to get max index (to get column) with loop
        mostSpaceString.split(" ").forEachIndexed { indexOfColumn, columnText ->

            t.forEachIndexed { indexOfRow, perRow -> // how much row that much loop to insert space

                val maxLengthOfColumn = getMaxLengthOfColumn(t, indexOfColumn)

                // Set space by column
                var mSpace = " "

                var len = 0 // find space insert position (char of each column + which column = len)
                try {
                    for (ii in 0..indexOfColumn) {
                        len += perRow.split(" ")[ii].count()
                    }
                } catch (e: IndexOutOfBoundsException) {
                    println(e.message)
                }

                len += indexOfColumn

                try {
                    if (len < maxLengthOfColumn) {
                        // How much space need
                        var spc = maxLengthOfColumn - len
                        // Generate space

                        for (i in 1..spc) {
                            mSpace += specialChar
                        }

                        println("$mSpace inside $indexOfRow, column $indexOfColumn, len: $len")

                        // Find new column item count for each loop
//                    var cItem = perRow.split(" ")[index1]
                        println("Per row: $perRow")

                        t.set(indexOfRow, perRow.insert(len, mSpace)) // todo insert perfectly

                    }

                } catch (e: IndexOutOfBoundsException) {
                    println(e.message)
                }

            }



            println("================================= Cycle END of: $indexOfColumn")

        }

        printRes(t)

//    Replace special char to space
        t.forEachIndexed { index, str ->
            t.set(index, str.replace(specialChar, " "))
        }
        printRes(t)

        return t

    }


    private fun getMaxLengthOfColumn(mArrayOfString: ArrayList<String>, indexOfColumn: Int): Int {

        var maxLengthOfColumn = 0

        mArrayOfString.forEachIndexed { indexOfRow, perRow -> // how much row that much loop to get max length of column

            var len = 0
            try {
                for (ii in 0..indexOfColumn) {
                    len += perRow.split(" ")[ii].count()
                }
            } catch (e: IndexOutOfBoundsException) {
                println(e.message)
            }

            if (len > maxLengthOfColumn) {
                maxLengthOfColumn = len
            }

        }

        maxLengthOfColumn += indexOfColumn // to add space in length

        println("Column $indexOfColumn Max Length: ${maxLengthOfColumn}")

        return maxLengthOfColumn

    } // getMaxLengthOfColumn


    fun String.insert(index: Int, string: String): String {
        println("\n")
        println("Replace String: $string")
        println("Main String: $this")
        println("Insert Position: $index")
        val updatedString = this.substring(0, index) + string + this.substring(index, this.length).trim()
        println("Updated String: $updatedString")
        println("\n")
        return updatedString
    }

    fun printRes(ar: ArrayList<String>) {
        println("\n=============== RESULT ==================")
        ar.forEach {
            println(it)
        }
        println("================= END ====================\n")
    } // printRes

    /**
     * This method will make all white spaces equal before of all string
     */
    private fun getAllPreSpacesEqual(myStringArray: List<String>): ArrayList<String> {

        // Keep all white space in another array
        // to manage starting indent spaces

        val whiteSpaceKeeper = arrayListOf<String>()

        myStringArray.forEachIndexed { index, s ->

            var allSpace = ""
            if (s.startsWith(" ")) {
                for (i in 1..s.count()) {
                    if (s[i - 1] == ' ') {
                        allSpace += " "
                    } else {
                        break
                    }
                }
            }
            whiteSpaceKeeper.add(allSpace)

        }

        val tempMyStringArray = arrayListOf<String>()

        // After keeping all pre white space we need to remove multiple white space inside code
        tempMyStringArray.addAll(removeMultiWhiteSpaceOfCode(myStringArray))



        // If all space are same size then no need to add max size of space
//        if (isAllAreSameItem(whiteSpaceKeeper)) {
//            return tempMyStringArray // TODO if all has no space before code
//        }

        // Add max size of pre white space in all array item
        whiteSpaceKeeper.max()?.let { maxAmountOfSpace ->

//            myStringArray.forEachIndexed { index, s ->
//                tempMyStringArray[index] = if (whiteSpaceKeeper[index].isNotEmpty()) {
//                    s.replace(whiteSpaceKeeper[index], maxAmountOfSpace)
//                } else {
//                    maxAmountOfSpace + s
//                }
//            }

            val p = arrayListOf<String>()
            tempMyStringArray.forEachIndexed { index, s ->

                p.add(maxAmountOfSpace + s)

            }

            tempMyStringArray.clear()
            tempMyStringArray.addAll(p)

        }



        return tempMyStringArray

    }

    private fun removeEmptyLineFromArray(myStringArray: List<String>): ArrayList<String> {

        val tempArray = arrayListOf<String>()
        tempArray.addAll(myStringArray.filterNot { it == "" })

        return tempArray

    } // removeEmptyLineFromArray

    /**
     * To check all array item are same or not
     */
    @Deprecated("This is no longer needed as we are trimming the code in removeMultiWhiteSpaceOfCode() method")
    private fun isAllAreSameItem(myStringArray: List<String>): Boolean {
        var allAreSame = true

        myStringArray.forEachIndexed { index, s ->

            if (index != 0) { // As first loop has nothing to compare
                if (myStringArray[index - 1] != s) {
                    allAreSame = false
                    return@forEachIndexed
                }
            }

        }

        return allAreSame
    } // isAllAreSameItem

    /**
     * This method will remove multiple white space of code except double quote part
     */
    private fun removeMultiWhiteSpaceOfCode(myStringArray: List<String>): ArrayList<String> {

        val resultArr = arrayListOf<String>()
        // Use this after preserve pre white space for indentation
        myStringArray.forEachIndexed { index, myString ->

            // Find pre quote part of code
            var myResult = ""
            if (myString.contains("\"")) {
                val firstQuoteIndex = myString.indexOf("\"")
                val firstPartOfQuote = myString.substring(0, firstQuoteIndex)
                myResult = firstPartOfQuote.trim().replace("\\s+".toRegex(), " ")
                myResult = myResult + " " + myString.substring(firstQuoteIndex, myString.length)
            } else {
                myResult = myString.trim().replace("\\s+".toRegex(), " ")
            }

            // Add pre white space for indentation
            myResult = myResult
            resultArr.add(myResult)
        }

        return resultArr

    } // removeMultiWhiteSpaceOfCode


}