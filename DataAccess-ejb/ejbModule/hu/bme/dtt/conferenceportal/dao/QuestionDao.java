package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Question;
import hu.futurion.mt.dao.GenericDao;

import javax.ejb.Local;

@Local
public interface QuestionDao extends GenericDao<Question> {

}
