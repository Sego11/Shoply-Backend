package com.Shoply_Backend.repositories;

import com.Shoply_Backend.domain.entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  CartItemRepository extends JpaRepository<CartItemEntity,Long > {
}
