package com.example.projekt2

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform