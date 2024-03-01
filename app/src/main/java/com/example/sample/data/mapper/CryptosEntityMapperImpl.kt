package com.example.sample.data.mapper

import com.example.sample.data.enitity.CryptoDataEntity


/**
 * Created by ArunKumar M on 29/02/2024.
 *
 */
object CryptosEntityMapperImpl  : CryptoEntityMapper<CryptoDataEntity> {
    override fun fromEntity(entity: CryptoDataEntity): com.example.sample.domain.model.Crypto {
        return com.example.sample.domain.model.Crypto(name = entity.name , symbol =  entity.symbol , isNew = entity.isNew , isActive = entity.isActive , type =  entity.type , id = entity.id)
    }

    override fun toEntity(model: com.example.sample.domain.model.Crypto): CryptoDataEntity {
        return CryptoDataEntity(name = model.name , symbol =  model.symbol , isNew = model.isNew , isActive = model.isActive , type =  model.type , id = model.id)
    }


}
