create table new_test(
id_new date,
start_date date,
end_date date
);

ALTER TABLE new_test add CONSTRAINT "test" check (end_date > start_date) enable;
ALTER TABLE new_test add CONSTRAINT "1test" check (id_new > end_date) enable;
desc new_test;
drop table new_test;
ALTER TABLE new_test ALTER COLUMN id_new number(30);
ALTER TABLE new_test Drop COLUMN id_new;
commit;

select * from new_test;