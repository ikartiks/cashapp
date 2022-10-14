package com.kartik.grevocab.api.pojos

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

@Keep
data class Stock(
    @SerializedName("ticker") var ticker: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("current_price_cents") var currentPriceCents: BigDecimal? = null,
    @SerializedName("quantity") var quantity: Int? = null,
    @SerializedName("current_price_timestamp") var currentPriceTimestamp: Long? = null
)
