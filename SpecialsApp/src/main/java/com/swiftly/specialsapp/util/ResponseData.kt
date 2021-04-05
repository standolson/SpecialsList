package com.swiftly.specialsapp.util

class ResponseData<D>
{
    var data: D? = null
    var errorStatus: String? = null

    constructor(resultStatus: String?) {
        this.errorStatus = resultStatus
    }

    constructor(data: D) {
        this.data = data
    }

    public fun hasStatusMessage() : Boolean = errorStatus != null
}