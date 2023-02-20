package com.revature.service;

public interface ServiceGenerics {
    public <T> T convertToObject(String json, Class <T> returnType);
}
