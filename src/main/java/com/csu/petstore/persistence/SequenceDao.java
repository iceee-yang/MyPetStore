package com.csu.petstore.persistence;

public interface SequenceDao {
    int getNextId(String name);
}
