package com.Shoply_Backend.mappers;

public interface Mapper <Entity,DTO>{

    DTO mapTo(Entity entity);
    Entity mapFrom(DTO dto);
}
