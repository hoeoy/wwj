
-- 卡表优化
alter table card_card add index idx_pk_staff(pk_staff) using btree;

-- 部门表优化
alter table db_department add index idx_code_name(department_code,department_name) using btree;

-- 人员表
alter table db_staff drop index index_staff_code;
alter table db_staff add index idx_staff_code_name(staff_code,staff_name) using btree;
alter table db_staff add index idx_staff_dpt_cmp(department_code,company_code) using btree;

