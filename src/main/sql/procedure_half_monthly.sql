/*1 传入半月记录表名称，将数据全部移动到meal_record*/
DELIMITER //
DROP PROCEDURE
IF EXISTS `ETL_meal_record_half_monthly`//

CREATE PROCEDURE ETL_meal_record_half_monthly(
	IN meal_record_yyyy_mm_dd VARCHAR(256)
)
BEGIN

SET @tbl_name = CONCAT("" , meal_record_yyyy_mm_dd) ;
SET @sql_etl = CONCAT(
	"INSERT INTO meal_record(
		from_table_pk_meal_record ,
		pk_staff ,
		pk_card ,
		pk_device ,
		card_code ,
		staff_code ,
		device_code ,
		meal_money ,
		meal_cash_money ,
		meal_allowance ,
		cash_retain ,
		allowance_retain ,
		money_retain ,
		meal_type ,
		meal_way ,
		device_meal_type ,
		meal_ts ,
		dining_name ,
		dining_code ,
		serial ,
		memo ,
		def1 ,
		def2 ,
		def3 ,
		def4 ,
		def5 ,
		ts ,
		staff_name ,
		staff_type ,
		company_code ,
		department_code ,
		company_name ,
		department_name ,
		card_state ,
		device_name ,
		pk_merchant ,
		merchant_code ,
		merchant_name ,
		meal_ts_day
	) SELECT
		r.pk_meal_record ,
		r.pk_staff ,
		r.pk_card ,
		r.pk_device ,
		r.card_code ,
		r.staff_code ,
		r.device_code ,
		r.meal_money ,
		r.meal_cash_money ,
		r.meal_allowance ,
		r.cash_retain ,
		r.allowance_retain ,
		r.money_retain ,
		r.meal_type ,
		r.meal_way ,
		r.device_meal_type ,
		r.meal_ts ,
		r.dining_name ,
		r.dining_code ,
		r.serial ,
		r.memo ,
		r.def1 ,
		r.def2 ,
		r.def3 ,
		r.def4 ,
		r.def5 ,
		r.ts ,
		s.staff_name ,
		s.staff_type ,
		s.company_code ,
		s.department_code ,
		c.company_name ,
		d.department_name ,
		card.card_state ,
		device.device_name ,
		device.pk_merchant ,
		m.merchant_code ,
		m.merchant_name,
	 substring(r.meal_ts,1,10) meal_ts_day
	FROM " ,@tbl_name ,
	" r LEFT JOIN db_staff s ON r.pk_staff = s.pk_staff
	LEFT JOIN db_company c ON s.company_code = c.company_code
	LEFT JOIN db_department d ON s.department_code = d.department_code
	LEFT JOIN card_card card ON r.pk_card = card.pk_card
	LEFT JOIN meal_device device ON r.pk_device = device.pk_device
	LEFT JOIN meal_merchant m ON device.pk_merchant = m.pk_merchant "
) ; PREPARE sql_etl FROM @sql_etl ; EXECUTE sql_etl ; END//
DELIMITER ;