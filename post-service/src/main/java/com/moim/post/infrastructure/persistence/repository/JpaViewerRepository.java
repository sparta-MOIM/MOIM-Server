package com.moim.post.infrastructure.persistence.repository;

import com.moim.post.domain.feed.Viewer;
import com.moim.post.domain.repository.ViewerRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaViewerRepository extends JpaRepository<Viewer, Long>, ViewerRepository {
}
