package com.senlainc.javacourses.petushokvaliantsin.repositoryapi.advertisement;

import com.senlainc.javacourses.petushokvaliantsin.model.State;
import com.senlainc.javacourses.petushokvaliantsin.model.advertisement.Advertisement;
import com.senlainc.javacourses.petushokvaliantsin.model.user.User;
import com.senlainc.javacourses.petushokvaliantsin.repositoryapi.IGenericDao;
import com.senlainc.javacourses.petushokvaliantsin.utility.page.IFilterParameter;
import com.senlainc.javacourses.petushokvaliantsin.utility.page.IPageParameter;
import com.senlainc.javacourses.petushokvaliantsin.utility.page.IStateParameter;

import java.util.List;

public interface IAdvertisementDao extends IGenericDao<Advertisement, Long> {

    List<Advertisement> readAllWithUser(IPageParameter pageParameter, User user, State state);

    List<Advertisement> readAllWithFilter(IPageParameter pageParameter, IFilterParameter filterParameter, IStateParameter stateParameter);

    Long readCountWitFilter(IFilterParameter filterParameter, IStateParameter stateParameter);

    List<Advertisement> tryJdbcTemplate();
}
