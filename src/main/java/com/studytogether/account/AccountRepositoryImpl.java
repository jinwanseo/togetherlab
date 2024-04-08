package com.studytogether.account;

import com.studytogether.domain.Account;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public boolean existsByEmail(String email) {
        return true;
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return true;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Account> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Account> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Account getOne(Long aLong) {
        return null;
    }

    @Override
    public Account getById(Long aLong) {
        return null;
    }

    @Override
    public Account getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Account> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Account> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Account> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Account> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Account, R> R findBy(Example<S> example,
        Function<FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Account> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Account> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Account> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Account> findAll() {
        return List.of();
    }

    @Override
    public List<Account> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Account entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Account> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Account> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return null;
    }
}
