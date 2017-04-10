package com.yalantis.jellyanimation.demo

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import java.text.AttributedCharacterIterator


/**
 * Created by arvin on 2017-4-9.
 */
open class MyView : TextView {
    constructor(cxt: Context) : super(cxt)

    constructor(cxt: Context, attr: AttributeSet) : super(cxt, attr) {
        init(cxt);
    }

    var cxt: Context? = null

    open fun init(cxt: Context) {
        this.cxt = cxt;
    }

}

fun PARSEINT(i: Int): String {
    return i.toString()
}