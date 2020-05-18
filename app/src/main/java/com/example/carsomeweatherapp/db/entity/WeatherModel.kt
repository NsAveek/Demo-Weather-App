package com.example.carsomeweatherapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class WeatherModel (
        @PrimaryKey var uid : String,
        @ColumnInfo(name = "payment_type") var paymentType : String?, // Cash or Card or Bank transaction
        @ColumnInfo(name = "category") var category : String?, // Category of the income, i.e : Salary
        @ColumnInfo(name = "purpose") var purpose : String?, // Note on the transaction
        @ColumnInfo(name = "amount") var amount : Double?, // Amount to be credited
        @ColumnInfo(name = "date") var date : String?, // Date to add the transaction
        @Ignore var type : String? = null
){
    constructor():this("","","","",0.0,"","")
}

