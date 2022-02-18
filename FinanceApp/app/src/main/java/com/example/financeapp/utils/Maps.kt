package com.example.financeapp.utils

object Maps {
    var months: Map<Int, String> = mapOf(
        0 to "Janeiro",
        1 to "Fevereiro",
        2 to "Março",
        3 to "Abril",
        4 to "Maio",
        5 to "Junho",
        6 to "Julho",
        7 to "Agosto",
        8 to "Setembro",
        9 to "Outubro",
        10 to "Novembro",
        11 to "Dezembro",
    )

    var types: Map<String, String> = mapOf(
        Constants.DEBT_TEXT to Constants.DEBT,
        Constants.PROFIT_TEXT to Constants.PROFIT
    )
}
