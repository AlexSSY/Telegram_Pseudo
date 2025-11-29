package rx.dagger.pseudo.data

fun countriesWithISOCodes(): List<CountryCode> {
    return listOf<CountryCode>(
        CountryCode("1", "USA"),
        CountryCode("380", "Украина"),
        CountryCode("7", "Russia"),
    )
}