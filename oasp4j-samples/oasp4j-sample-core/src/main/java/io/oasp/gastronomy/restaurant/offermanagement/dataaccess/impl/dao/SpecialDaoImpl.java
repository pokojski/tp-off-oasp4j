package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationDaoImpl;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SpecialDao;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SpecialSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import javax.inject.Named;

import com.mysema.query.alias.Alias;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;

/**
 * This is the implementation of {@link SpecialDao}.
 */
@Named
public class SpecialDaoImpl extends ApplicationDaoImpl<SpecialEntity> implements SpecialDao {

  /**
   * The constructor.
   */
  public SpecialDaoImpl() {

    super();
  }

  @Override
  public Class<SpecialEntity> getEntityClass() {

    return SpecialEntity.class;
  }

  @Override
  public PaginatedListTo<SpecialEntity> findSpecials(SpecialSearchCriteriaTo criteria) {

    SpecialEntity special = Alias.alias(SpecialEntity.class);
    EntityPathBase<SpecialEntity> alias = Alias.$(special);
    JPAQuery query = new JPAQuery(getEntityManager()).from(alias);

    String name = criteria.getName();
    if (name != null) {
      query.where(Alias.$(special.getName()).eq(name));
    }

    Long offer = criteria.getOfferId();
    if (offer != null) {
      query.where(Alias.$(special.getOfferId()).eq(offer));
    }

    // WeeklyPeriodSearchCriteriaTo weeklyPeriodSearchCriteriaTo = criteria.getActivePeriod();
    // WeeklyPeriodEmbeddable weeklyPeriodEmbeddable = new WeeklyPeriodEmbeddable();
    // weeklyPeriodEmbeddable.setStartingDay(weeklyPeriodSearchCriteriaTo.getStartingDay());
    // weeklyPeriodEmbeddable.setEndingDay(weeklyPeriodSearchCriteriaTo.getEndingDay());
    // weeklyPeriodEmbeddable.setStartingHour(weeklyPeriodSearchCriteriaTo.getStartingHour());
    // weeklyPeriodEmbeddable.setEndingHour(weeklyPeriodSearchCriteriaTo.getEndingHour());

    query.where(Alias.$(special.getActivePeriod().getStartingDay()).eq(criteria.getActivePeriod().getStartingDay())
        .and(Alias.$(special.getActivePeriod().getStartingDay()).eq(criteria.getActivePeriod().getStartingDay()))
        .and(Alias.$(special.getActivePeriod().getStartingHour()).eq(criteria.getActivePeriod().getStartingHour()))
        .and(Alias.$(special.getActivePeriod().getEndingDay()).eq(criteria.getActivePeriod().getEndingDay()))
        .and(Alias.$(special.getActivePeriod().getEndingHour()).eq(criteria.getActivePeriod().getEndingHour())));

    Money specialPrice = criteria.getSpecialPrice();
    if (specialPrice != null) {
      query.where(Alias.$(special.getSpecialPrice()).eq(specialPrice));
    }

    return findPaginated(criteria, query, alias);
  }

}