package br.com.ac.urlchecker.repository;

import br.com.ac.urlchecker.entity.WhitelistItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aléx Carvalho
 */
public interface WhitelistRepository extends JpaRepository<WhitelistItem, Long> {

    Page<WhitelistItem> findByClientOrClientIsNull(String client, Pageable pageRequest);
}
