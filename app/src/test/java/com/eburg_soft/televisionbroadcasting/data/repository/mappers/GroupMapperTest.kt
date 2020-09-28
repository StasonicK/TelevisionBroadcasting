package com.eburg_soft.televisionbroadcasting.data.repository.mappers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eburg_soft.televisionbroadcasting.utils.TestUtil
import io.mockk.*
import org.junit.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Test
import org.junit.rules.*

class GroupMapperTest {

    /*
        Single GroupResponse
        -   map
    */
    @Test
    @Throws(Exception::class)
    fun convertSingleGroupResponse() {
        //  Arrange
        val response = arrayListOf(TestUtil.TEST_GROUP_RESPONSE_1)
        val expectedEntity = TestUtil.TEST_GROUP_ENTITY_1

        // Act
        val resultEntity = GroupMapper.map(response).keys.toList()[0]

        // Assert
        Assertions.assertNotNull(resultEntity)
        Assertions.assertEquals(expectedEntity, resultEntity)
    }

    /*
        Many GroupResponses
        -   map
    */
    @Test
    @Throws(Exception::class)
    fun convertManyGroupResponses() {
        //  Arrange
        val response = TestUtil.TEST_GROUP_RESPONSES
        val expectedEntity = TestUtil.TEST_GROUP_ENTITIES

        // Act
        val resultEntity = GroupMapper.map(response).keys.toList()

        // Assert
        Assertions.assertNotNull(resultEntity)
        Assertions.assertEquals(expectedEntity, resultEntity)
    }
}