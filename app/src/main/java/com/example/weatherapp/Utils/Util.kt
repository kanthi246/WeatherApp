package com.example.supersub.Utils

import android.content.Context
import android.text.format.DateFormat
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.weatherapp.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun getProgressDrawable(context: Context):CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth=4f
        centerRadius=30f
        start()
    }
}

//we have added new method to load images through network in to imageview by using ktx
fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable){
    val options=RequestOptions().placeholder(progressDrawable).error(R.drawable.ic_image)
    Glide.with(context).setDefaultRequestOptions(options).load(uri).into(this)
}

@BindingAdapter("android:imageUrl")
fun loadimage(view: ImageView, uri: String?){
    view.loadImage(uri, getProgressDrawable(view.context))
}

fun getDate(time: Long): String? {
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = time * 1000
    return DateFormat.format("dd-MM-yyyy", cal).toString()
}

fun getday(value:String):String{
    val inFormat = SimpleDateFormat("dd-MM-yyyy")
    var date:Date=inFormat.parse(value)
    val outFormat = SimpleDateFormat("EEEE")
    val day = outFormat.format(date)
    return day
}

//we have added new method to load images locally in to imageview by using ktx
fun ImageView.getIcon(value:Int,view: ImageView){
    when (value) {
        1 -> view.setImageResource(R.drawable.one)
        2 -> view.setImageResource(R.drawable.two)
        3 -> view.setImageResource(R.drawable.three)
        4 -> view.setImageResource(R.drawable.four)
        5 -> view.setImageResource(R.drawable.five)
        6 -> view.setImageResource(R.drawable.six)
        7 -> view.setImageResource(R.drawable.seven)
        8 -> view.setImageResource(R.drawable.eight)
        11 -> view.setImageResource(R.drawable.eleven)
        12 -> view.setImageResource(R.drawable.twelve)
        13 -> view.setImageResource(R.drawable.thirteen)
        14 -> view.setImageResource(R.drawable.fourteen)
        15 -> view.setImageResource(R.drawable.fifteen)
        16 -> view.setImageResource(R.drawable.sixteen)
        17 -> view.setImageResource(R.drawable.seventeen)
        19 -> view.setImageResource(R.drawable.ninteen)
        20 -> view.setImageResource(R.drawable.twenty)
        21 -> view.setImageResource(R.drawable.twentyone)
        22 -> view.setImageResource(R.drawable.twentytwo)
        23 -> view.setImageResource(R.drawable.twentythree)
        24 -> view.setImageResource(R.drawable.twentyfour)
        25 -> view.setImageResource(R.drawable.twentyfive)
        26 -> view.setImageResource(R.drawable.twentysix)
        29 -> view.setImageResource(R.drawable.twentynine)
        30 -> view.setImageResource(R.drawable.thirty)
        31 -> view.setImageResource(R.drawable.thirtyone)
        32 -> view.setImageResource(R.drawable.thirtytwo)
        33 -> view.setImageResource(R.drawable.thirtythree)
        34 -> view.setImageResource(R.drawable.thirtyfour)
        35 -> view.setImageResource(R.drawable.thirtyfive)
        36 -> view.setImageResource(R.drawable.thirtysix)
        37 -> view.setImageResource(R.drawable.thirtyseven)
        38 -> view.setImageResource(R.drawable.thirtyeight)
        39 -> view.setImageResource(R.drawable.thirtynine)
        40 -> view.setImageResource(R.drawable.fourty)
        41 -> view.setImageResource(R.drawable.fourtyone)
        42 -> view.setImageResource(R.drawable.fourtytwo)
        43 -> view.setImageResource(R.drawable.fourtythree)
        44 -> view.setImageResource(R.drawable.fourtyfour)
        else -> view.setImageResource(R.drawable.one)
    }
}
