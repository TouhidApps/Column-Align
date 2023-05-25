package com.touhidapps.align

import java.util.regex.Matcher
import java.util.regex.Pattern

class MyStringProcess {

    fun getResultAlignedArray(myMultiLineString: String): ArrayList<String> {

        if (!myMultiLineString.contains("\n")) {
            return arrayListOf()
        }

        // Check any array item starts with white space or not
        val rmvEmptyLineArray = removeEmptyLineFromArray(myMultiLineString.split("\n"))
        val mData = getAllPreSpacesEqual(rmvEmptyLineArray)
        val specialChars = hashMapOf<Char, Char>().apply {
            put(' ', '⍛')
            put(':', '⏧')
            put('=', '〄')
        }
        val s = arrayListOf<String>()
        val t = arrayListOf<String>()

        // Insert special character to string value (inside of double quote) part to ignore formatting them
        mData.forEachIndexed { index, str ->

            var mStr = str

            if (str.contains("\"")) {

                val p: Pattern = Pattern.compile("\"([^\"]*)\"")
                val m: Matcher = p.matcher(str)

                while (m.find()) { // If multiple section of double quote it will loop
                    val a = m.group(0)
                        .replace(specialChars.keys.elementAt(0), specialChars[specialChars.keys.elementAt(0)]!!)
                        .replace(specialChars.keys.elementAt(1), specialChars[specialChars.keys.elementAt(1)]!!)
                        .replace(specialChars.keys.elementAt(2), specialChars[specialChars.keys.elementAt(2)]!!)
                    mStr = str.replaceRange(m.start(), m.end(), a)
                }

            }

//            mStr = mStr.replace(":", " : ")
//                .replace("=", " = ")

            s.add(mStr)
            t.add(mStr)

        }

        // Find most space contained string
        var mostSpaceString = ""

        s.forEachIndexed { index, item ->
            if (index == 0) {
                mostSpaceString = item
            } else {
                if (s[index - 1].split(' ').count() < item.split(' ').count()) {
                    mostSpaceString = item
                }
            }
        }

        // By column, used max column contained string
        // This is only to get max index (to get column) with loop
        mostSpaceString.split(' ').forEachIndexed { indexOfColumn, columnText ->

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
                        val spc = maxLengthOfColumn - len
                        // Generate space
                        for (i in 1..spc) {
                            mSpace += specialChars[specialChars.keys.elementAt(0)]!!
                        }
                        // Find new column item count for each loop
                        t[indexOfRow] = perRow.insert(len, mSpace)
                    }
                } catch (e: IndexOutOfBoundsException) {
                    println(e.message)
                }

            }

        }

        printRes(t)

//    Replace special char to space
        t.forEachIndexed { index, str ->
            t[index] = str
                .replace(specialChars[specialChars.keys.elementAt(0)]!!, specialChars.keys.elementAt(0))
                .replace(specialChars[specialChars.keys.elementAt(1)]!!, specialChars.keys.elementAt(1))
                .replace(specialChars[specialChars.keys.elementAt(2)]!!, specialChars.keys.elementAt(2))
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
        return maxLengthOfColumn

    } // getMaxLengthOfColumn


    private fun String.insert(index: Int, string: String): String {
        return substring(0, index) + string + substring(index, length).trim()
    }

    private fun printRes(ar: ArrayList<String>) {
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

        // Add max size of pre white space in all array item
        whiteSpaceKeeper.max()?.let { maxAmountOfSpace ->

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