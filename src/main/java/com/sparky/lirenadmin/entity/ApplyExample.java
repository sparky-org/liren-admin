package com.sparky.lirenadmin.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ApplyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ApplyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andApplyEmpNoIsNull() {
            addCriterion("apply_emp_no is null");
            return (Criteria) this;
        }

        public Criteria andApplyEmpNoIsNotNull() {
            addCriterion("apply_emp_no is not null");
            return (Criteria) this;
        }

        public Criteria andApplyEmpNoEqualTo(Long value) {
            addCriterion("apply_emp_no =", value, "applyEmpNo");
            return (Criteria) this;
        }

        public Criteria andApplyEmpNoNotEqualTo(Long value) {
            addCriterion("apply_emp_no <>", value, "applyEmpNo");
            return (Criteria) this;
        }

        public Criteria andApplyEmpNoGreaterThan(Long value) {
            addCriterion("apply_emp_no >", value, "applyEmpNo");
            return (Criteria) this;
        }

        public Criteria andApplyEmpNoGreaterThanOrEqualTo(Long value) {
            addCriterion("apply_emp_no >=", value, "applyEmpNo");
            return (Criteria) this;
        }

        public Criteria andApplyEmpNoLessThan(Long value) {
            addCriterion("apply_emp_no <", value, "applyEmpNo");
            return (Criteria) this;
        }

        public Criteria andApplyEmpNoLessThanOrEqualTo(Long value) {
            addCriterion("apply_emp_no <=", value, "applyEmpNo");
            return (Criteria) this;
        }

        public Criteria andApplyEmpNoIn(List<Long> values) {
            addCriterion("apply_emp_no in", values, "applyEmpNo");
            return (Criteria) this;
        }

        public Criteria andApplyEmpNoNotIn(List<Long> values) {
            addCriterion("apply_emp_no not in", values, "applyEmpNo");
            return (Criteria) this;
        }

        public Criteria andApplyEmpNoBetween(Long value1, Long value2) {
            addCriterion("apply_emp_no between", value1, value2, "applyEmpNo");
            return (Criteria) this;
        }

        public Criteria andApplyEmpNoNotBetween(Long value1, Long value2) {
            addCriterion("apply_emp_no not between", value1, value2, "applyEmpNo");
            return (Criteria) this;
        }

        public Criteria andAuditEmpNoIsNull() {
            addCriterion("audit_emp_no is null");
            return (Criteria) this;
        }

        public Criteria andAuditEmpNoIsNotNull() {
            addCriterion("audit_emp_no is not null");
            return (Criteria) this;
        }

        public Criteria andAuditEmpNoEqualTo(Long value) {
            addCriterion("audit_emp_no =", value, "auditEmpNo");
            return (Criteria) this;
        }

        public Criteria andAuditEmpNoNotEqualTo(Long value) {
            addCriterion("audit_emp_no <>", value, "auditEmpNo");
            return (Criteria) this;
        }

        public Criteria andAuditEmpNoGreaterThan(Long value) {
            addCriterion("audit_emp_no >", value, "auditEmpNo");
            return (Criteria) this;
        }

        public Criteria andAuditEmpNoGreaterThanOrEqualTo(Long value) {
            addCriterion("audit_emp_no >=", value, "auditEmpNo");
            return (Criteria) this;
        }

        public Criteria andAuditEmpNoLessThan(Long value) {
            addCriterion("audit_emp_no <", value, "auditEmpNo");
            return (Criteria) this;
        }

        public Criteria andAuditEmpNoLessThanOrEqualTo(Long value) {
            addCriterion("audit_emp_no <=", value, "auditEmpNo");
            return (Criteria) this;
        }

        public Criteria andAuditEmpNoIn(List<Long> values) {
            addCriterion("audit_emp_no in", values, "auditEmpNo");
            return (Criteria) this;
        }

        public Criteria andAuditEmpNoNotIn(List<Long> values) {
            addCriterion("audit_emp_no not in", values, "auditEmpNo");
            return (Criteria) this;
        }

        public Criteria andAuditEmpNoBetween(Long value1, Long value2) {
            addCriterion("audit_emp_no between", value1, value2, "auditEmpNo");
            return (Criteria) this;
        }

        public Criteria andAuditEmpNoNotBetween(Long value1, Long value2) {
            addCriterion("audit_emp_no not between", value1, value2, "auditEmpNo");
            return (Criteria) this;
        }

        public Criteria andApplyContentIsNull() {
            addCriterion("apply_content is null");
            return (Criteria) this;
        }

        public Criteria andApplyContentIsNotNull() {
            addCriterion("apply_content is not null");
            return (Criteria) this;
        }

        public Criteria andApplyContentEqualTo(String value) {
            addCriterion("apply_content =", value, "applyContent");
            return (Criteria) this;
        }

        public Criteria andApplyContentNotEqualTo(String value) {
            addCriterion("apply_content <>", value, "applyContent");
            return (Criteria) this;
        }

        public Criteria andApplyContentGreaterThan(String value) {
            addCriterion("apply_content >", value, "applyContent");
            return (Criteria) this;
        }

        public Criteria andApplyContentGreaterThanOrEqualTo(String value) {
            addCriterion("apply_content >=", value, "applyContent");
            return (Criteria) this;
        }

        public Criteria andApplyContentLessThan(String value) {
            addCriterion("apply_content <", value, "applyContent");
            return (Criteria) this;
        }

        public Criteria andApplyContentLessThanOrEqualTo(String value) {
            addCriterion("apply_content <=", value, "applyContent");
            return (Criteria) this;
        }

        public Criteria andApplyContentLike(String value) {
            addCriterion("apply_content like", value, "applyContent");
            return (Criteria) this;
        }

        public Criteria andApplyContentNotLike(String value) {
            addCriterion("apply_content not like", value, "applyContent");
            return (Criteria) this;
        }

        public Criteria andApplyContentIn(List<String> values) {
            addCriterion("apply_content in", values, "applyContent");
            return (Criteria) this;
        }

        public Criteria andApplyContentNotIn(List<String> values) {
            addCriterion("apply_content not in", values, "applyContent");
            return (Criteria) this;
        }

        public Criteria andApplyContentBetween(String value1, String value2) {
            addCriterion("apply_content between", value1, value2, "applyContent");
            return (Criteria) this;
        }

        public Criteria andApplyContentNotBetween(String value1, String value2) {
            addCriterion("apply_content not between", value1, value2, "applyContent");
            return (Criteria) this;
        }

        public Criteria andPicListIsNull() {
            addCriterion("pic_list is null");
            return (Criteria) this;
        }

        public Criteria andPicListIsNotNull() {
            addCriterion("pic_list is not null");
            return (Criteria) this;
        }

        public Criteria andPicListEqualTo(String value) {
            addCriterion("pic_list =", value, "picList");
            return (Criteria) this;
        }

        public Criteria andPicListNotEqualTo(String value) {
            addCriterion("pic_list <>", value, "picList");
            return (Criteria) this;
        }

        public Criteria andPicListGreaterThan(String value) {
            addCriterion("pic_list >", value, "picList");
            return (Criteria) this;
        }

        public Criteria andPicListGreaterThanOrEqualTo(String value) {
            addCriterion("pic_list >=", value, "picList");
            return (Criteria) this;
        }

        public Criteria andPicListLessThan(String value) {
            addCriterion("pic_list <", value, "picList");
            return (Criteria) this;
        }

        public Criteria andPicListLessThanOrEqualTo(String value) {
            addCriterion("pic_list <=", value, "picList");
            return (Criteria) this;
        }

        public Criteria andPicListLike(String value) {
            addCriterion("pic_list like", value, "picList");
            return (Criteria) this;
        }

        public Criteria andPicListNotLike(String value) {
            addCriterion("pic_list not like", value, "picList");
            return (Criteria) this;
        }

        public Criteria andPicListIn(List<String> values) {
            addCriterion("pic_list in", values, "picList");
            return (Criteria) this;
        }

        public Criteria andPicListNotIn(List<String> values) {
            addCriterion("pic_list not in", values, "picList");
            return (Criteria) this;
        }

        public Criteria andPicListBetween(String value1, String value2) {
            addCriterion("pic_list between", value1, value2, "picList");
            return (Criteria) this;
        }

        public Criteria andPicListNotBetween(String value1, String value2) {
            addCriterion("pic_list not between", value1, value2, "picList");
            return (Criteria) this;
        }

        public Criteria andOriginIsNull() {
            addCriterion("origin is null");
            return (Criteria) this;
        }

        public Criteria andOriginIsNotNull() {
            addCriterion("origin is not null");
            return (Criteria) this;
        }

        public Criteria andOriginEqualTo(String value) {
            addCriterion("origin =", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotEqualTo(String value) {
            addCriterion("origin <>", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginGreaterThan(String value) {
            addCriterion("origin >", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginGreaterThanOrEqualTo(String value) {
            addCriterion("origin >=", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLessThan(String value) {
            addCriterion("origin <", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLessThanOrEqualTo(String value) {
            addCriterion("origin <=", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLike(String value) {
            addCriterion("origin like", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotLike(String value) {
            addCriterion("origin not like", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginIn(List<String> values) {
            addCriterion("origin in", values, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotIn(List<String> values) {
            addCriterion("origin not in", values, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginBetween(String value1, String value2) {
            addCriterion("origin between", value1, value2, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotBetween(String value1, String value2) {
            addCriterion("origin not between", value1, value2, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNoIsNull() {
            addCriterion("origin_no is null");
            return (Criteria) this;
        }

        public Criteria andOriginNoIsNotNull() {
            addCriterion("origin_no is not null");
            return (Criteria) this;
        }

        public Criteria andOriginNoEqualTo(Long value) {
            addCriterion("origin_no =", value, "originNo");
            return (Criteria) this;
        }

        public Criteria andOriginNoNotEqualTo(Long value) {
            addCriterion("origin_no <>", value, "originNo");
            return (Criteria) this;
        }

        public Criteria andOriginNoGreaterThan(Long value) {
            addCriterion("origin_no >", value, "originNo");
            return (Criteria) this;
        }

        public Criteria andOriginNoGreaterThanOrEqualTo(Long value) {
            addCriterion("origin_no >=", value, "originNo");
            return (Criteria) this;
        }

        public Criteria andOriginNoLessThan(Long value) {
            addCriterion("origin_no <", value, "originNo");
            return (Criteria) this;
        }

        public Criteria andOriginNoLessThanOrEqualTo(Long value) {
            addCriterion("origin_no <=", value, "originNo");
            return (Criteria) this;
        }

        public Criteria andOriginNoIn(List<Long> values) {
            addCriterion("origin_no in", values, "originNo");
            return (Criteria) this;
        }

        public Criteria andOriginNoNotIn(List<Long> values) {
            addCriterion("origin_no not in", values, "originNo");
            return (Criteria) this;
        }

        public Criteria andOriginNoBetween(Long value1, Long value2) {
            addCriterion("origin_no between", value1, value2, "originNo");
            return (Criteria) this;
        }

        public Criteria andOriginNoNotBetween(Long value1, Long value2) {
            addCriterion("origin_no not between", value1, value2, "originNo");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIsNull() {
            addCriterion("audit_status is null");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIsNotNull() {
            addCriterion("audit_status is not null");
            return (Criteria) this;
        }

        public Criteria andAuditStatusEqualTo(String value) {
            addCriterion("audit_status =", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotEqualTo(String value) {
            addCriterion("audit_status <>", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusGreaterThan(String value) {
            addCriterion("audit_status >", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusGreaterThanOrEqualTo(String value) {
            addCriterion("audit_status >=", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLessThan(String value) {
            addCriterion("audit_status <", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLessThanOrEqualTo(String value) {
            addCriterion("audit_status <=", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLike(String value) {
            addCriterion("audit_status like", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotLike(String value) {
            addCriterion("audit_status not like", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIn(List<String> values) {
            addCriterion("audit_status in", values, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotIn(List<String> values) {
            addCriterion("audit_status not in", values, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusBetween(String value1, String value2) {
            addCriterion("audit_status between", value1, value2, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotBetween(String value1, String value2) {
            addCriterion("audit_status not between", value1, value2, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIsNull() {
            addCriterion("audit_time is null");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIsNotNull() {
            addCriterion("audit_time is not null");
            return (Criteria) this;
        }

        public Criteria andAuditTimeEqualTo(Date value) {
            addCriterionForJDBCDate("audit_time =", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("audit_time <>", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("audit_time >", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("audit_time >=", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeLessThan(Date value) {
            addCriterionForJDBCDate("audit_time <", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("audit_time <=", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIn(List<Date> values) {
            addCriterionForJDBCDate("audit_time in", values, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("audit_time not in", values, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("audit_time between", value1, value2, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("audit_time not between", value1, value2, "auditTime");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andShopNoIsNull() {
            addCriterion("shop_no is null");
            return (Criteria) this;
        }

        public Criteria andShopNoIsNotNull() {
            addCriterion("shop_no is not null");
            return (Criteria) this;
        }

        public Criteria andShopNoEqualTo(Long value) {
            addCriterion("shop_no =", value, "shopNo");
            return (Criteria) this;
        }

        public Criteria andShopNoNotEqualTo(Long value) {
            addCriterion("shop_no <>", value, "shopNo");
            return (Criteria) this;
        }

        public Criteria andShopNoGreaterThan(Long value) {
            addCriterion("shop_no >", value, "shopNo");
            return (Criteria) this;
        }

        public Criteria andShopNoGreaterThanOrEqualTo(Long value) {
            addCriterion("shop_no >=", value, "shopNo");
            return (Criteria) this;
        }

        public Criteria andShopNoLessThan(Long value) {
            addCriterion("shop_no <", value, "shopNo");
            return (Criteria) this;
        }

        public Criteria andShopNoLessThanOrEqualTo(Long value) {
            addCriterion("shop_no <=", value, "shopNo");
            return (Criteria) this;
        }

        public Criteria andShopNoIn(List<Long> values) {
            addCriterion("shop_no in", values, "shopNo");
            return (Criteria) this;
        }

        public Criteria andShopNoNotIn(List<Long> values) {
            addCriterion("shop_no not in", values, "shopNo");
            return (Criteria) this;
        }

        public Criteria andShopNoBetween(Long value1, Long value2) {
            addCriterion("shop_no between", value1, value2, "shopNo");
            return (Criteria) this;
        }

        public Criteria andShopNoNotBetween(Long value1, Long value2) {
            addCriterion("shop_no not between", value1, value2, "shopNo");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(Long value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(Long value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(Long value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(Long value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(Long value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(Long value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<Long> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<Long> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(Long value1, Long value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(Long value1, Long value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andIsValidIsNull() {
            addCriterion("is_valid is null");
            return (Criteria) this;
        }

        public Criteria andIsValidIsNotNull() {
            addCriterion("is_valid is not null");
            return (Criteria) this;
        }

        public Criteria andIsValidEqualTo(Boolean value) {
            addCriterion("is_valid =", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotEqualTo(Boolean value) {
            addCriterion("is_valid <>", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidGreaterThan(Boolean value) {
            addCriterion("is_valid >", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_valid >=", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLessThan(Boolean value) {
            addCriterion("is_valid <", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLessThanOrEqualTo(Boolean value) {
            addCriterion("is_valid <=", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidIn(List<Boolean> values) {
            addCriterion("is_valid in", values, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotIn(List<Boolean> values) {
            addCriterion("is_valid not in", values, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidBetween(Boolean value1, Boolean value2) {
            addCriterion("is_valid between", value1, value2, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_valid not between", value1, value2, "isValid");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtModifyIsNull() {
            addCriterion("gmt_modify is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifyIsNotNull() {
            addCriterion("gmt_modify is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifyEqualTo(Date value) {
            addCriterion("gmt_modify =", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyNotEqualTo(Date value) {
            addCriterion("gmt_modify <>", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyGreaterThan(Date value) {
            addCriterion("gmt_modify >", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modify >=", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyLessThan(Date value) {
            addCriterion("gmt_modify <", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modify <=", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyIn(List<Date> values) {
            addCriterion("gmt_modify in", values, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyNotIn(List<Date> values) {
            addCriterion("gmt_modify not in", values, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyBetween(Date value1, Date value2) {
            addCriterion("gmt_modify between", value1, value2, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modify not between", value1, value2, "gmtModify");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}