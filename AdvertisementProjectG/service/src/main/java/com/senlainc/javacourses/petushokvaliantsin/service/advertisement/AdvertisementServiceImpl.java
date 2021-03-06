package com.senlainc.javacourses.petushokvaliantsin.service.advertisement;

import com.senlainc.javacourses.petushokvaliantsin.dao.api.StateDao;
import com.senlainc.javacourses.petushokvaliantsin.dao.api.advertisement.AdvertisementDao;
import com.senlainc.javacourses.petushokvaliantsin.dao.api.user.UserDao;
import com.senlainc.javacourses.petushokvaliantsin.dto.StateDto;
import com.senlainc.javacourses.petushokvaliantsin.dto.advertisement.AdvertisementDto;
import com.senlainc.javacourses.petushokvaliantsin.dto.combination.ResultListDto;
import com.senlainc.javacourses.petushokvaliantsin.enumeration.EnumException;
import com.senlainc.javacourses.petushokvaliantsin.enumeration.EnumLogger;
import com.senlainc.javacourses.petushokvaliantsin.enumeration.EnumState;
import com.senlainc.javacourses.petushokvaliantsin.enumeration.GraphProperty;
import com.senlainc.javacourses.petushokvaliantsin.model.State;
import com.senlainc.javacourses.petushokvaliantsin.model.advertisement.Advertisement;
import com.senlainc.javacourses.petushokvaliantsin.model.advertisement.Advertisement_;
import com.senlainc.javacourses.petushokvaliantsin.model.user.User;
import com.senlainc.javacourses.petushokvaliantsin.model.user.User_;
import com.senlainc.javacourses.petushokvaliantsin.service.AbstractService;
import com.senlainc.javacourses.petushokvaliantsin.service.api.advertisement.AdvertisementService;
import com.senlainc.javacourses.petushokvaliantsin.utility.exception.EntityNotAvailableException;
import com.senlainc.javacourses.petushokvaliantsin.utility.exception.EntityNotExistException;
import com.senlainc.javacourses.petushokvaliantsin.utility.exception.PermissionDeniedException;
import com.senlainc.javacourses.petushokvaliantsin.utility.mapper.annotation.SingularClass;
import com.senlainc.javacourses.petushokvaliantsin.utility.mapper.annotation.SingularModel;
import com.senlainc.javacourses.petushokvaliantsin.utility.page.IFilterParameter;
import com.senlainc.javacourses.petushokvaliantsin.utility.page.IStateParameter;
import com.senlainc.javacourses.petushokvaliantsin.utility.page.implementation.FilterParameter;
import com.senlainc.javacourses.petushokvaliantsin.utility.page.implementation.PageParameter;
import com.senlainc.javacourses.petushokvaliantsin.utility.page.implementation.StateParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@Service
@SingularClass
@RequiredArgsConstructor
public class AdvertisementServiceImpl extends AbstractService implements AdvertisementService {

    private static final String SORT_FIELD = "advertisement-";
    private static final String SORT_PARAMETER_SEPARATOR = "-";
    private final AdvertisementDao advertisementDao;
    private final StateDao stateDao;
    private final UserDao userDao;

    @Override
    @Transactional
    public boolean create(String username, AdvertisementDto advertisementDto) {
        final Advertisement advertisement = dtoMapper.map(advertisementDto, Advertisement.class);
        advertisement.setUser(userDao.readByUserCred(username, GraphProperty.User.DEFAULT).orElseThrow(() ->
                new EntityNotExistException(entityNotExistMessage(2, 2, username))));
        advertisement.setDate(LocalDate.now());
        advertisement.setState(stateDao.readByDescription(EnumState.MODERATION.name()).orElseThrow(() ->
                new EntityNotExistException(entityNotExistMessage(1, 1, EnumState.MODERATION.name()))));
        advertisementDao.save(advertisement);
        log.info(EnumLogger.SUCCESSFUL_CREATE.getText());
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public boolean delete(String username, Long index) {
        final Advertisement advertisement = advertisementDao.readById(index).orElseThrow(() ->
                new EntityNotExistException(entityNotExistMessage(0, 0, index)));
        checkPermission(advertisement, username);
        advertisement.setState(stateDao.readByDescription(EnumState.DISABLED.name()).orElseThrow(() ->
                new EntityNotExistException(entityNotExistMessage(1, 1, EnumState.DISABLED.name()))));
        advertisementDao.save(advertisement);
        log.info(EnumLogger.SUCCESSFUL_DELETE.getText());
        return true;
    }

    @Override
    @Transactional
    public boolean updateByUser(String username, AdvertisementDto advertisementDto) {
        checkPermission(advertisementDao.readById(advertisementDto.getId()).orElseThrow(() ->
                new EntityNotExistException(entityNotExistMessage(0, 0, advertisementDto.getId()))), username);
        final Advertisement advertisement = dtoMapper.map(advertisementDto, Advertisement.class);
        advertisement.setDate(LocalDate.now());
        advertisement.setState(stateDao.readByDescription(EnumState.MODERATION.name()).orElseThrow(() ->
                new EntityNotExistException(entityNotExistMessage(1, 1, EnumState.MODERATION.name()))));
        advertisementDao.save(advertisement);
        log.info(EnumLogger.SUCCESSFUL_UPDATE.getText());
        return true;
    }

    @Override
    @Transactional
    public boolean updateStateByModerator(Long index, StateDto state) {
        final Advertisement advertisement = advertisementDao.readById(index).orElseThrow();
        advertisement.setState(stateDao.readByDescription(state.getDescription()).orElseThrow(() ->
                new EntityNotExistException(entityNotExistMessage(1, 1, state.getDescription()))));
        advertisementDao.save(advertisement);
        log.info(EnumLogger.SUCCESSFUL_UPDATE.getText());
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public AdvertisementDto readByUser(Long index) {
        final Advertisement advertisement = advertisementDao.readById(index).orElseThrow();
        if (!advertisement.getState().getId().equals(stateDao.readByDescription(EnumState.ACTIVE.name()).orElseThrow(() ->
                new EntityNotExistException(entityNotExistMessage(1, 1, EnumState.ACTIVE.name()))).getId())) {
            throw new EntityNotAvailableException(EnumException.PERMISSION.getMessage());
        }
        final AdvertisementDto result = dtoMapper.map(advertisement, AdvertisementDto.class);
        log.info(EnumLogger.SUCCESSFUL_READ.getText());
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public AdvertisementDto readByModerator(Long index) {
        final AdvertisementDto result = dtoMapper.map(advertisementDao.readById(index).orElseThrow(() ->
                        new EntityNotExistException(entityNotExistMessage(0, 0, index))),
                AdvertisementDto.class);
        log.info(EnumLogger.SUCCESSFUL_READ.getText());
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public ResultListDto<AdvertisementDto> readAllWithUser(Long userIndex, int page, int numberElements, EnumState enumState) {
        final User user = userDao.readById(userIndex).orElseThrow(() ->
                new EntityNotExistException(entityNotExistMessage(2, 0, userIndex)));
        final State state = stateDao.readByDescription(enumState.name()).orElseThrow(() ->
                new EntityNotExistException(entityNotExistMessage(1, 1, EnumState.ACTIVE.name())));
        final Long resultSize = advertisementDao.countAdvertisementByUserAndState(user, state);
        final List<AdvertisementDto> result = dtoMapper.mapAll(advertisementDao.readAllByUserAndState(user, state,
                PageRequest.of(page, numberElements)), AdvertisementDto.class);
        log.info(EnumLogger.SUCCESSFUL_READ.getText());
        return ResultListDto.of(resultSize, result);
    }

    @Override
    @Transactional(readOnly = true)
    @SingularModel(metamodels = {Advertisement_.class, User_.class})
    public List<AdvertisementDto> readAll(int page, int numberElements, String direction, String sortField, String search,
                                          String category, double minPrice, double maxPrice, EnumState advertisementState) {
        final PageParameter pageParameter = splitSortFiled(page, numberElements, direction, sortField);
        final IFilterParameter filterParameter = FilterParameter.of(search, category, minPrice, maxPrice);
        final IStateParameter stateParameter = StateParameter.of(stateDao.readByDescription(advertisementState.name()).orElse(null),
                stateDao.readByDescription(EnumState.APPROVED.name()).orElseThrow(() ->
                        new EntityNotExistException(entityNotExistMessage(1, 1, EnumState.APPROVED.name()))));
        final List<AdvertisementDto> result = dtoMapper.mapAll(advertisementDao.readAllWithFilter(pageParameter, filterParameter, stateParameter),
                AdvertisementDto.class);
        log.info(EnumLogger.SUCCESSFUL_READ.getText());
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Long readSize() {
        return advertisementDao.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Long readSize(String search, String category, double minPrice, double maxPrice, EnumState advertisementState) {
        final IFilterParameter filterParameter = FilterParameter.of(search, category, minPrice, maxPrice);
        final IStateParameter stateParameter = StateParameter.of(stateDao.readByDescription(advertisementState.name()).orElse(null),
                stateDao.readByDescription(EnumState.APPROVED.name()).orElseThrow(() ->
                        new EntityNotExistException(entityNotExistMessage(1, 1, EnumState.APPROVED.name()))));
        return advertisementDao.readCountWitFilter(filterParameter, stateParameter);
    }

    private PageParameter splitSortFiled(int page, int numberElements, String direction, String sortField) {
        if (sortField.contains(SORT_PARAMETER_SEPARATOR)) {
            return PageParameter.of(page, numberElements, direction,
                    singularMapper.getAttribute(SORT_FIELD + sortField.split(SORT_PARAMETER_SEPARATOR)[0]),
                    singularMapper.getAttribute(sortField));
        }
        return PageParameter.of(page, numberElements, direction, singularMapper.getAttribute(SORT_FIELD + sortField));
    }

    private void checkPermission(Advertisement advertisement, String username) {
        if (!advertisement.getUser().getId().equals(userDao.readByUserCred(username, GraphProperty.User.DEFAULT).orElseThrow(() ->
                new EntityNotExistException(entityNotExistMessage(2, 2, username))).getId())) {
            throw new PermissionDeniedException(EnumException.PERMISSION.getMessage());
        }
    }
}
