package com.example.notekeeperapp

data class Note(
  var id: Int = 0,
  var title: String,
  var content: String,
  var timestamp: Long
)