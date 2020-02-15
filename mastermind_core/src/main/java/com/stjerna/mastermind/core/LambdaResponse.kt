package com.stjerna.mastermind.core

interface LambdaResponse

class ErrorResponse(val errorCode: String): LambdaResponse