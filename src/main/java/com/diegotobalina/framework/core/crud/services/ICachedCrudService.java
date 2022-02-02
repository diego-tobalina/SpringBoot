package com.diegotobalina.framework.core.crud.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface ICachedCrudService<T> extends ICrudService<T> {

    /**
     * Devuelve la entidad filtrando por el id
     *
     * @param id Id sobre el que se filtra
     * @return La entidad encontrada
     */
    @Cacheable(value = "cachedFindById", keyGenerator = "customKeyGenerator")
    default T cachedFindById(long id, JpaRepository<T, Long> repository) {
        return findById(id, repository);
    }

    /**
     * Devuelve la entidad filtrando por el id, en el caso de no encontrarla devuelve null
     *
     * @param id Id sobre el que se filtra
     * @return La entidad encontrada o null
     * @throws IllegalArgumentException Si el id es null o está vacío
     */
    @Cacheable(value = "cachedFindByIdOrNull", keyGenerator = "customKeyGenerator")
    default T cachedFindByIdOrNull(long id, JpaRepository<T, Long> repository) {
        return findByIdOrNull(id, repository);
    }

    /**
     * Devuelve todas las entidades que encuentre con los ids
     *
     * @param ids Ids sobre los que filtrar
     * @return La lista con las entidades encontradas
     * @throws IllegalArgumentException Si el ids es null
     */
    @Cacheable(value = "cachedFindAllByIds", keyGenerator = "customKeyGenerator")
    default List<T> cachedFindAllByIds(List<Long> ids, JpaRepository<T, Long> repository) {
        return findAllByIds(ids, repository);
    }

    /**
     * Guarda la entidad, si no existía la crea y si existía la actualiza
     *
     * @param t Entidad que quieres guardar
     * @return Devuelve la entidad creada o actualizada
     * @throws IllegalArgumentException Si la entidad es null
     */
    @Cacheable(value = "cachedSave", keyGenerator = "customKeyGenerator")
    default T cachedSave(T t, JpaRepository<T, Long> repository) {
        return save(t, repository);
    }

    /**
     * Guarda todas las entidades, las que no existen las crea y las que existen las actualiza
     *
     * @param t Lista con las entidades que quieres crear o actualizar
     * @return Devuelve la lista de las entidades creadas o actualizadas
     * @throws IllegalArgumentException Si la lista de entidades es null
     */
    @Cacheable(value = "cachedSaveAll", keyGenerator = "customKeyGenerator")
    default List<T> cachedSaveAll(List<T> t, JpaRepository<T, Long> repository) {
        return saveAll(t, repository);
    }

    /**
     * Borra la entidad que coincide con el id
     *
     * @param id Id de la entidad que quieres borrar
     * @throws IllegalArgumentException Si el id es null o está vacío
     */
    @Cacheable(value = "cachedDeleteById", keyGenerator = "customKeyGenerator")
    default void cachedDeleteById(long id, JpaRepository<T, Long> repository) {
        deleteById(id, repository);
    }

    /**
     * Borra las entidades que coinciden con el id
     *
     * @param ids Lista de Ids de las entidades que quieres borrar
     * @throws IllegalArgumentException Si el id es null
     */
    @Cacheable(value = "cachedDeleteAllById", keyGenerator = "customKeyGenerator")
    default void cachedDeleteAllById(List<Long> ids, JpaRepository<T, Long> repository) {
        deleteAllById(ids, repository);
    }

    @Scheduled(fixedDelay = 60000)
    @CacheEvict(value = "cachedFindById", allEntries = true)
    default void cacheEvictCachedFindById() {
        // empty
    }

    @Scheduled(fixedDelay = 60000)
    @CacheEvict(value = "cachedFindByIdOrNull", allEntries = true)
    default void cacheEvictCachedFindByIdOrNull() {
        // empty
    }

    @Scheduled(fixedDelay = 60000)
    @CacheEvict(value = "cachedFindAllByIds", allEntries = true)
    default void cacheEvictCachedFindAllByIds() {
        // empty
    }

    @Scheduled(fixedDelay = 60000)
    @CacheEvict(value = "cachedSave", allEntries = true)
    default void cacheEvictCachedSave() {
        // empty
    }

    @Scheduled(fixedDelay = 60000)
    @CacheEvict(value = "cachedSaveAll", allEntries = true)
    default void cacheEvictCachedSaveAll() {
        // empty
    }

    @Scheduled(fixedDelay = 60000)
    @CacheEvict(value = "cachedDeleteById", allEntries = true)
    default void cacheEvictCachedDeleteById() {
        // empty
    }

    @Scheduled(fixedDelay = 60000)
    @CacheEvict(value = "cachedDeleteAllByIds", allEntries = true)
    default void cacheEvictCachedDeleteAllByIds() {
        // empty
    }
}
