package com.example.meteohub.utils

/**
 * @author Bulat Bagaviev
 * @created 21.11.2021
 */

class Utility {
/*
    private fun loadToDb() {
        Thread {
            val cityListTmp2 = prepareCsv()
            db.cityDao().insertAll(cityListTmp2)

            Log.d("TAG", "loadToDb finished")
        }.start()
    }

    private fun prepareCsv(): List<City> {
        val cityListTmp = arrayListOf<City>()
        val `is` = resources.openRawResource(R.raw.translated)

        try {
            BufferedReader(InputStreamReader(`is`, "UTF-8")).use { reader ->
                while (reader.ready()) {
                    val data = reader.readLine().split(";".toRegex()).toTypedArray()

                    cityListTmp.add(City(
                        Integer.valueOf(data[0]),
                        data[1],
                        data[2],
                        Double.valueOf(data[3]),
                        Double.valueOf(data[4]))
                    )
                }
            }
        } catch (e: IOException) {
            Log.e("TAG", "prepareCsv: $e")
        }
        return cityListTmp
    }
}
 */
}