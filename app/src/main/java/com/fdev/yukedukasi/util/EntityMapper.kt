package com.fdev.yukedukasi.util

abstract class EntityMapper<Entity , Domain>{

    abstract fun mapDomainToEntity(domain: Domain) : Entity

    abstract fun mapEntityToDomain(entity: Entity) : Domain



}