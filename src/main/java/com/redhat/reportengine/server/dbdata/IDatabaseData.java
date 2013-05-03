package com.redhat.reportengine.server.dbdata;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 01, 2013
 */
public interface IDatabaseData<T,U> {
	T getAll();
	T getById(U... args);
	T getByName(U... args);
}
