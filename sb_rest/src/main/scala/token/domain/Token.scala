package token.domain

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

import scala.annotation.meta.field

@Document
case class Token (
  id: String,
  @(Indexed@field)(unique = true)
  token: String,
  used: Boolean
)