package com.moim.post.infrastructure.persistence.repository.jpa;

import com.moim.post.domain.feed.Viewer;
import com.moim.post.domain.repository.command.ViewerCommandRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaViewerRepository extends JpaRepository<Viewer, Long>, ViewerCommandRepository {
}

