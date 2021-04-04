package com.swiftly.specialsapp.util

class ResponseData<D>
{
    var data: D? = null
    var resultStatus: String? = null

    constructor(resultStatus: String?) {
        this.resultStatus = resultStatus
    }

    constructor(data: D) {
        this.data = data
    }

    public fun hasStatusMessage() : Boolean = resultStatus != null
}