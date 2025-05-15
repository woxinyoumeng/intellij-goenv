package com.github.heyvito.goenv.services

val commentLineRegexp = Regex("^\\s*#")
val privateLineRegexp = Regex("\\s*private (.+)")
val insecureLineRegexp = Regex("\\s*insecure (.+)")

class EnvParser(file: ByteArray) {
    var privates: List<String>
    var insecures: List<String>

    init {
        val fileStr = String(file).split("\n")
        val privates = arrayListOf<String>()
        val insecures = arrayListOf<String>()
        for (l in fileStr) {
            if (commentLineRegexp matches l) {
                continue
            }

            if (privateLineRegexp matches l) {
                val components = privateLineRegexp.matchEntire(l)!!.groupValues
                val url = components.elementAt(1)
                privates.add(url)
            }
            if (insecureLineRegexp matches l) {
                val components = insecureLineRegexp.matchEntire(l)!!.groupValues
                val url = components.elementAt(1)
                insecures.add(url)
            }
        }

        this.privates = privates
        this.insecures = insecures
    }
}
