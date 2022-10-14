package com.bootexample.spring.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootexample.spring.models.Photo;

public interface PhotoRepo extends JpaRepository<Photo, Long> {
	Photo findByTitle(String title);
}
