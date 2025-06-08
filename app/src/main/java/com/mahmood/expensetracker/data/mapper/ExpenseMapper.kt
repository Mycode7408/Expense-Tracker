package com.mahmood.expensetracker.data.mapper

import com.mahmood.expensetracker.data.local.entity.ExpenseEntity
import com.mahmood.expensetracker.domain.model.Expense

/**
 * Mapper class to convert between domain model (Expense) and data layer entity (ExpenseEntity).
 */
class ExpenseMapper {
    
    /**
     * Maps an ExpenseEntity to an Expense domain model.
     *
     * @param entity The ExpenseEntity to map.
     * @return The mapped Expense domain model.
     */
    fun mapToDomain(entity: ExpenseEntity): Expense {
        return Expense(
            id = entity.id,
            title = entity.title,
            amount = entity.amount,
            category = entity.category,
            timestamp = entity.timestamp,
            description = entity.description
        )
    }
    
    /**
     * Maps an Expense domain model to an ExpenseEntity.
     *
     * @param domainModel The Expense domain model to map.
     * @return The mapped ExpenseEntity.
     */
    fun mapToEntity(domainModel: Expense): ExpenseEntity {
        return ExpenseEntity(
            id = domainModel.id,
            title = domainModel.title,
            amount = domainModel.amount,
            category = domainModel.category,
            timestamp = domainModel.timestamp,
            description = domainModel.description
        )
    }
    
    /**
     * Maps a list of ExpenseEntity objects to a list of Expense domain models.
     *
     * @param entities The list of ExpenseEntity objects to map.
     * @return The mapped list of Expense domain models.
     */
    fun mapToDomainList(entities: List<ExpenseEntity>): List<Expense> {
        return entities.map { mapToDomain(it) }
    }
}