package com.tamimtechnology.roommateplus.respositories;

import com.tamimtechnology.roommateplus.model.BusinessEntities.TakeOutTrash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrashRepository extends JpaRepository<TakeOutTrash, Long> {
}
