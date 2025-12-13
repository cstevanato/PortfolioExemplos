package com.example.portfolio.exemplos.features.flowTesting.data

import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    val cep: String?,
    val logradouro: String?,
    val complemento: String?,
    val unidade: String?,
    val bairro: String?,
    val localidade: String?,
    val uf: String?,
    val estado: String?,
    val regiao: String?,
    val ddd: String?,
)