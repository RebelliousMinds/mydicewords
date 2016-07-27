package token.domain

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

import scala.annotation.meta.field

@Document
case class Word (
  id: String,
  @(Indexed@field)(unique = true)
  wordNumber: String,
  @(Indexed@field)(unique = true)
  word: String
)